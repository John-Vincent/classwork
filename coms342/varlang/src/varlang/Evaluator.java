package varlang;
import static varlang.AST.*;
import static varlang.Value.*;

import java.util.List;
import java.util.ArrayList;

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
import varlang.Printer.Formatter;

public class Evaluator implements Visitor<Value> {

  ArrayList<String> substitutions;
  Formatter f = new Formatter();

	Value valueOf(Program p) {
		Env env = new EmptyEnv();
    this.substitutions = new ArrayList<String>();
		// Value of a program in this language is the value of the expression
		return (Value) p.accept(this, env);
	}

	@Override
	public Value visit(AddExp e, Env env) {
		List<Exp> operands = e.all();
		double result = 0;
    int i = 0;
    Value v = null, temp;
    try{
  		for(i = 0; i < operands.size(); i++) {
        v = (Value) operands.get(i).accept(this, env);
  			NumVal intermediate = (NumVal) v; // Ddynamic type-checking
  			result += intermediate.v(); //Semantics of AddExp in terms of the target language.
  		}
  		return new NumVal(result);
    } catch(ClassCastException error){
      for(; i < operands.size(); i++){
        temp = (Value) operands.get(i).accept(this, env);
        if(temp instanceof DynamicError){
          ((DynamicError) temp).append((DynamicError) v);
          v = temp;
        }
      }
      return v;
    }
	}

	@Override
	public Value visit(NumExp e, Env env) {
		return new NumVal(e.v());
	}

	@Override
	public Value visit(DivExp e, Env env) {
		List<Exp> operands = e.all();
    Value v = null, temp;
    int i = 1;
    try{
      v = (Value) operands.get(0).accept(this, env);
  		NumVal lVal = (NumVal) v;
  		double result = lVal.v();
  		for(i=1; i<operands.size(); i++) {
        v = (Value) operands.get(i).accept(this, env);
  			NumVal rVal = (NumVal) v;
  			result = result / rVal.v();
  		}
  		return new NumVal(result);
    } catch(ClassCastException error){
      for(; i < operands.size(); i++){
        temp = (Value) operands.get(i).accept(this, env);
        if(temp instanceof DynamicError){
          ((DynamicError) temp).append((DynamicError) v);
          v = temp;
        }
      }
      return v;
    }
	}

	@Override
	public Value visit(MultExp e, Env env) {
		List<Exp> operands = e.all();
    Value v = null, temp;
    int i = 0;
		double result = 1;
    try{
  		for(; i < operands.size(); i++) {
        v = (Value) operands.get(i).accept(this, env);
  			NumVal intermediate = (NumVal) v; // Dynamic type-checking
  			result *= intermediate.v(); //Semantics of MultExp.
  		}
  		return new NumVal(result);
    } catch(ClassCastException error){
      for(; i < operands.size(); i++){
        temp = (Value) operands.get(i).accept(this, env);
        if(temp instanceof DynamicError){
          ((DynamicError) temp).append((DynamicError) v);
          v = temp;
        }
      }
      return v;
    }
	}

	@Override
	public Value visit(Program p, Env env) {
		return (Value) p.e().accept(this, env);
	}

	@Override
	public Value visit(SubExp e, Env env) {
		List<Exp> operands = e.all();
    Value v = null, temp;
    int i = 1;
    try{
      v = (Value) operands.get(0).accept(this, env);
  		NumVal lVal = (NumVal) v;
  		double result = lVal.v();
  		for(; i<operands.size(); i++) {
        v = (Value) operands.get(i).accept(this, env);
  			NumVal rVal = (NumVal) v;
  			result = result - rVal.v();
  		}
  		return new NumVal(result);
    } catch(ClassCastException error){
      for(; i < operands.size(); i++){
        temp = (Value) operands.get(i).accept(this, env);
        if(temp instanceof DynamicError){
          ((DynamicError) temp).append((DynamicError) v);
          v = temp;
        }
      }
      return v;
    }
	}

	@Override
	public Value visit(VarExp e, Env env) {
		// Previously, all variables had value 42. New semantics.
		return env.get(e.name());
	}

	@Override
	public Value visit(LetExp e, Env env) { // New for varlang.
    Value ans = null, result;
    List<String> names = e.names();
    ArrayList<String> names2 = new ArrayList<String>();
		List<Exp> value_exps = e.value_exps();

		Env new_env = env;
		for (int i = 0; i < names.size(); i++){
      if(this.substitutions.contains(names.get(i))){
        ans = new DynamicError("Error hole in scope: " + e.accept(f, env));
      } else{
        this.substitutions.add(names.get(i));
      }
      if(names2.contains(names.get(i))){
        if(ans == null){
          ans = new DynamicError("variable " + names.get(i) + " defined multiple times: " + e.accept(f, env));
        } else{
          ((DynamicError)ans).append(new DynamicError("variable " + names.get(i) + " defined multiple times: " + e.accept(f, env)));
        }
      }
      names2.add(names.get(i));
      result = (Value)value_exps.get(i).accept(this,env);
			new_env = new ExtendEnv(new_env, names.get(i), result);
    }
    result = (Value) (e.body().accept(this, new_env));
    if(ans instanceof DynamicError){
      if(result instanceof DynamicError){
        ((DynamicError)result).append((DynamicError)ans);
        ans = result;
      }
      return ans;
    }
    return result;
	}

  public Value visit(LetsExp e, Env env) {
    Value ans = null, result;
    List<String> names = e.names();
    ArrayList<String> names2 = new ArrayList<String>();
		List<Exp> value_exps = e.value_exps();
    for(String n : names){
      if(this.substitutions.contains(n)){
        ans = new DynamicError("Error hole in scope: " + e.accept(this.f, env));
      } else{
        this.substitutions.add(n);
      }
      if(names2.contains(n)){
        if(ans == null){
          ans = new DynamicError("variable " + n + " defined multiple times: " + e.accept(f, env));
        } else{
          ((DynamicError)ans).append(new DynamicError("variable " + n + " defined multiple times: " + e.accept(f, env)));
        }
      }
      names2.add(n);
    }
    result = (Value) ((Exp)e.body().subst(names, value_exps)).accept(this, env);
    if(ans instanceof DynamicError){
      if(result instanceof DynamicError){
        ((DynamicError)result).append((DynamicError)ans);
        ans = result;
      }
      return ans;
    }
    return result;
  }

}
