package varlang;

import java.util.ArrayList;
import java.util.List;

/**
 * This class hierarchy represents expressions in the abstract syntax tree
 * manipulated by this interpreter.
 *
 * @author hridesh
 *
 */
@SuppressWarnings("rawtypes")
public interface AST {
	public static abstract class ASTNode implements AST {
		public abstract Object accept(Visitor visitor, Env env);
    public abstract ASTNode subst(List<String> id, List<Exp> val);
	}

	public static class Program extends ASTNode {
		Exp _e;

		public Program(Exp e) {
			_e = e;
		}

		public Exp e() {
			return _e;
		}

    public ASTNode subst(List<String> id, List<Exp> val){
      return new Program((Exp)this._e.subst(id, val));
    }

		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public static abstract class Exp extends ASTNode {

	}

	public static class VarExp extends Exp {
		String _name;

		public VarExp(String name) {
			_name = name;
		}

		public String name() {
			return _name;
		}

    public ASTNode subst(List<String> id, List<Exp> val){
      for(int i = 0; i < id.size(); i++){
        if(id.get(i).equals(this._name)){
          return val.get(i);
        }
      }
      return new VarExp(this._name);
    }

		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public static class NumExp extends Exp {
		double _val;

		public NumExp(double v) {
			_val = v;
		}

		public double v() {
			return _val;
		}

    public ASTNode subst(List<String> id, List<Exp> val){
      return new NumExp(this._val);
    }

		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public static abstract class CompoundArithExp extends Exp {
		List<Exp> _rest;

		public CompoundArithExp() {
			_rest = new ArrayList<Exp>();
		}

		public CompoundArithExp(Exp fst) {
			_rest = new ArrayList<Exp>();
			_rest.add(fst);
		}

		public CompoundArithExp(List<Exp> args) {
			_rest = new ArrayList<Exp>();
			for (Exp e : args)
				_rest.add((Exp) e);
		}

		public CompoundArithExp(Exp fst, List<Exp> rest) {
			_rest = new ArrayList<Exp>();
			_rest.add(fst);
			_rest.addAll(rest);
		}

		public CompoundArithExp(Exp fst, Exp second) {
			_rest = new ArrayList<Exp>();
			_rest.add(fst);
			_rest.add(second);
		}

		public Exp fst() {
			return _rest.get(0);
		}

		public Exp snd() {
			return _rest.get(1);
		}

		public List<Exp> all() {
			return _rest;
		}

    public ASTNode subst(List<String> id, List<Exp> val){
      ArrayList<Exp> n = new ArrayList<Exp>();
      for(Exp e : this._rest){
        n.add(e.subst(id, val));
      }
    }

		public void add(Exp e) {
			_rest.add(e);
		}

	}

	public static class AddExp extends CompoundArithExp {
		public AddExp(Exp fst) {
			super(fst);
		}

		public AddExp(List<Exp> args) {
			super(args);
		}

		public AddExp(Exp fst, List<Exp> rest) {
			super(fst, rest);
		}

		public AddExp(Exp left, Exp right) {
			super(left, right);
		}

		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public static class SubExp extends CompoundArithExp {

		public SubExp(Exp fst) {
			super(fst);
		}

		public SubExp(List<Exp> args) {
			super(args);
		}

		public SubExp(Exp fst, List<Exp> rest) {
			super(fst, rest);
		}

		public SubExp(Exp left, Exp right) {
			super(left, right);
		}

		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public static class DivExp extends CompoundArithExp {
		public DivExp(Exp fst) {
			super(fst);
		}

		public DivExp(List<Exp> args) {
			super(args);
		}

		public DivExp(Exp fst, List<Exp> rest) {
			super(fst, rest);
		}

		public DivExp(Exp left, Exp right) {
			super(left, right);
		}

		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public static class MultExp extends CompoundArithExp {
		public MultExp(Exp fst) {
			super(fst);
		}

		public MultExp(List<Exp> args) {
			super(args);
		}

		public MultExp(Exp fst, List<Exp> rest) {
			super(fst, rest);
		}

		public MultExp(Exp left, Exp right) {
			super(left, right);
		}

		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	/**
	 * A let expression has the syntax
	 *
	 *  (let ((name expression)* ) expression)
	 *
	 * @author hridesh
	 *
	 */
	public static class LetExp extends Exp {
		List<String> _names;
		List<Exp> _value_exps;
		Exp _body;

		public LetExp(List<String> names, List<Exp> value_exps, Exp body) {
			_names = names;
			_value_exps = value_exps;
			_body = body;
		}

		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}

		public List<String> names() { return _names; }

		public List<Exp> value_exps() { return _value_exps; }

		public Exp body() { return _body; }

    public ASTNode subst(List<String> id, List<Exp> val){
      boolean hole = false;
      ArrayList<String> new_id = new ArrayList<String>();
      ArrayList<Exp> new_val = new ArrayList<Exp>();
      ArrayList<String> new_names = new ArrayList<String>();
      ArrayList<Exp> new_value_exps = new ArrayList<Exp>();

      //all value substitutions are done
      //first id hole is checked for
      for(int i = 0; i < this._names.size(); i++){
        if(this._names.get(i).equals(id.get(0))){
          hole = true;
        }
        new_names.add(this._names.get(i));
        new_value_exps.add(this._value_exps.get(i).subst(id, val));
      }
      if(!hole){
        new_id.add(id.get(0));
        new_val.add(val.get(0));
      }

      for(int j = 1; j < id.size(); j++){
        hole = false;
        for(int i = 0; i < this._names.size(); i++){
          if(this._names.get(i).equals(id.get(j))){
            hole = true;
          }
        }
        if(!hole){
          new_id.add(id.get(0));
          new_val.add(val.get(0));
        }
      }
      return new LetExp(new_names, new_value_exps, this.body.subst(new_id, new_val));
    }

	}

  public static class LetsExp extends Exp {
		List<String> _names;
		List<Exp> _value_exps;
		Exp _body;

		public LetsExp(List<String> names, List<Exp> value_exps, Exp body) {
			_names = names;
			_value_exps = value_exps;
			_body = body;
		}

		public Object accept(Visitor visitor, Env env) {
			return visitor.visit(this, env);
		}

		public List<String> names() { return _names; }

		public List<Exp> value_exps() { return _value_exps; }

		public Exp body() { return _body; }

    public ASTNode subst(List<String> id, List<Exp> val){
      boolean hole = false;
      ArrayList<String> new_id = new ArrayList<String>();
      ArrayList<Exp> new_val = new ArrayList<Exp>();
      ArrayList<String> new_names = new ArrayList<String>();
      ArrayList<Exp> new_value_exps = new ArrayList<Exp>();

      //all value substitutions are done
      //first id hole is checked for
      for(int i = 0; i < this._names.size(); i++){
        if(this._names.get(i).equals(id.get(0))){
          hole = true;
        }
        new_names.add(this._names.get(i));
        new_value_exps.add(this._value_exps.get(i).subst(id, val));
      }
      if(!hole){
        new_id.add(id.get(0));
        new_val.add(val.get(0));
      }

      for(int j = 1; j < id.size(); j++){
        hole = false;
        for(int i = 0; i < this._names.size(); i++){
          if(this._names.get(i).equals(id.get(j))){
            hole = true;
          }
        }
        if(!hole){
          new_id.add(id.get(0));
          new_val.add(val.get(0));
        }
      }
      return new LetExp(new_names, new_value_exps, this.body.subst(new_id, new_val));
    }
	}

	public interface Visitor <T> {
		// This interface should contain a signature for each concrete AST node.
		public T visit(AST.AddExp e, Env env);
		public T visit(AST.NumExp e, Env env);
		public T visit(AST.DivExp e, Env env);
		public T visit(AST.MultExp e, Env env);
		public T visit(AST.Program p, Env env);
		public T visit(AST.SubExp e, Env env);
		public T visit(AST.VarExp e, Env env);
		public T visit(AST.LetExp e, Env env); // New for the varlang
	}
}
