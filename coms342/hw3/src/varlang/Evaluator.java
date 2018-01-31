package varlang;
import static varlang.AST.*;
import static varlang.Value.*;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import varlang.AST.AddExp;
import varlang.AST.NumExp;
import varlang.AST.DivExp;
import varlang.AST.MultExp;
import varlang.AST.Program;
import varlang.AST.SubExp;
import varlang.AST.VarExp;
import varlang.AST.Visitor;
import varlang.Env.EmptyEnv;
import varlang.Env.ExtendEnv;

public class Evaluator implements Visitor<Value> {

  HashMap<String, Boolean> vars;

	Value valueOf(Program p) {
    this.vars = new HashMap<String, Boolean>();
		Env env = new EmptyEnv();
    env = new ExtendEnv(env, "January", new NumVal(1));
    env = new ExtendEnv(env, "February", new NumVal(2));
    env = new ExtendEnv(env, "March", new NumVal(3));
    env = new ExtendEnv(env, "April", new NumVal(4));
    env = new ExtendEnv(env, "May", new NumVal(5));
    env = new ExtendEnv(env, "June", new NumVal(6));
    env = new ExtendEnv(env, "July", new NumVal(7));
    env = new ExtendEnv(env, "August", new NumVal(8));
    env = new ExtendEnv(env, "September", new NumVal(9));
    env = new ExtendEnv(env, "October", new NumVal(10));
    env = new ExtendEnv(env, "Novemeber", new NumVal(11));
    env = new ExtendEnv(env, "December", new NumVal(12));
		// Value of a program in this language is the value of the expression
		Value ans = (Value) p.accept(this, env);
    String warn = "";
    for(String key: vars.keySet()){
      if(!vars.get(key)){
        warn += "\n(" + key + " was not used)";
      }
    }
    if(!warn.equals("")){
      ans = new WarnVal(ans.toString() + warn);
    }

    return ans;
	}

	@Override
	public Value visit(AddExp e, Env env) {
		List<Exp> operands = e.all();
		double result = 0;
		for(Exp exp: operands) {
			NumVal intermediate = (NumVal) exp.accept(this, env); // Dynamic type-checking
			result += intermediate.v(); //Semantics of AddExp in terms of the target language.
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(NumExp e, Env env) {
		return new NumVal(e.v());
	}

	@Override
	public Value visit(DivExp e, Env env) {
		List<Exp> operands = e.all();
		NumVal lVal = (NumVal) operands.get(0).accept(this, env);
		double result = lVal.v();
		for(int i=1; i<operands.size(); i++) {
			NumVal rVal = (NumVal) operands.get(i).accept(this, env);
			result = result / rVal.v();
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(MultExp e, Env env) {
		List<Exp> operands = e.all();
		double result = 1;
		for(Exp exp: operands) {
			NumVal intermediate = (NumVal) exp.accept(this, env); // Dynamic type-checking
			result *= intermediate.v(); //Semantics of MultExp.
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(Program p, Env env) {
		return (Value) p.e().accept(this, env);
	}

	@Override
	public Value visit(SubExp e, Env env) {
		List<Exp> operands = e.all();
		NumVal lVal = (NumVal) operands.get(0).accept(this, env);
		double result = lVal.v();
		for(int i=1; i<operands.size(); i++) {
			NumVal rVal = (NumVal) operands.get(i).accept(this, env);
			result = result - rVal.v();
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(VarExp e, Env env) {
		// Previously, all variables had value 42. New semantics.
		if(this.vars.containsKey(e.name())){
      this.vars.put(e.name(), true);
    }
		return env.get(e.name());
	}

  @Override
	public Value visit(DecExp e, Env env) {
		// Previously, all variables had value 42. New semantics.
		try{
      if(this.vars.containsKey(e.name())){
        this.vars.put(e.name(), true);
      }
      return new NumVal(((NumVal) env.get(e.name())).v() - e.key());
    } catch(ClassCastException ex){
      return env.get(e.name());
    }
	}

	@Override
	public Value visit(LetExp e, Env env) { // New for varlang.
		List<String> names = e.names();
		List<Exp> value_exps = e.value_exps();
		List<Value> values = new ArrayList<Value>(value_exps.size());

		for(Exp exp : value_exps)
			values.add((Value)exp.accept(this, env));

		Env new_env = env;
		for (int i = 0; i < names.size(); i++){
      if(!this.vars.containsKey(names.get(i))){
        this.vars.put(names.get(i), false);
      }
			new_env = new ExtendEnv(new_env, names.get(i), values.get(i));
    }
		return (Value) e.body().accept(this, new_env);
	}

  @Override
  public Value visit(LeteExp e, Env env) { // New for varlang.
    List<String> names = e.names();
    List<Exp> value_exps = e.value_exps();
    List<Value> values = new ArrayList<Value>(value_exps.size());

    for(Exp exp : value_exps)
      values.add((Value)exp.accept(this, env));

    Env new_env = env;
    for (int i = 0; i < names.size(); i++){
      try{
        if(!this.vars.containsKey(names.get(i))){
          this.vars.put(names.get(i), false);
        }
        new_env = new ExtendEnv(new_env, names.get(i), new NumVal(((NumVal)values.get(i)).v() + e.getKey()));
      } catch(ClassCastException ex){
        return values.get(i);
      }
    }

    return (Value) e.body().accept(this, new_env);
  }

}
