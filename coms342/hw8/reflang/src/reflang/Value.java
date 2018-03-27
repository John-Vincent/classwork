package reflang;

import java.util.List;

import reflang.AST.Exp;

public interface Value {
	public String tostring();
  public void reachable(Heap h);
	static class RefVal implements Value { //New in the reflang
		private int _loc = -1;
		public RefVal(int loc) { _loc = loc; }
		public String tostring() {
			return "loc:" + this._loc;
		}
		public int loc() { return _loc; }
    @Override
    public void reachable(Heap h){
      h.deref(this).reachable(h);
      h.mark(this, false);
    }
	}
	static class FunVal implements Value { //New in the funclang
		private Env _env;
		private List<String> _formals;
		private Exp _body;
		public FunVal(Env env, List<String> formals, Exp body) {
			_env = env;
			_formals = formals;
			_body = body;
		}
		public Env env() { return _env; }
		public List<String> formals() { return _formals; }
		public Exp body() { return _body; }
	    public String tostring() {
			String result = "(lambda ( ";
			for(String formal : _formals)
				result += formal + " ";
			result += ") ";
			result += _body.accept(new Printer.Formatter(), _env);
			return result + ")";
	    }
    @Override
    public void reachable(Heap h){
      h.mark(this, false);
    }
	}
  static class ArrayVal implements Value{
    private NumVal[][][] array;
    public ArrayVal(int i, int j, int k){
      this.array = new NumVal[k][i][j];
    }
    public String tostring() {
      String s = "";
      for(int i = 0; i < array.length; i++){
        if(i == 0 && array.length > 1)
          s += "[\n";
        for(int j = 0; j < array[i].length; j++){
          if(j == 0 && array[i].length > 1)
            s += "  [\n";
          for(int k = 0; k < array[i][j].length; k++){
            if(k == 0)
              s += "    ";
            if(k == 0 && array[i][j].length > 1)
              s += "[";
            if(array[i][j][k] == null)
              s += " 0";
            else
              s += " " + array[i][j][k].v();
          }
          if(array[i][j].length > 1)
            s += " ]";
          s += "\n";
        }
        if(array[i].length > 1)
          s += "  ]\n";
      }
      if(array.length > 1)
        s+= "]";
      return s;
    }
    public void set(int[] i, NumVal n){
      array[i[2]][i[0]][i[1]] = n;
    }
    public NumVal get(int ... i){
      if(this.array[i[2]][i[0]][i[1]] == null)
        return new NumVal(0);
      return this.array[i[2]][i[0]][i[1]];
    }
    public int getDim(){
      int i = 1;
      if(array.length > 1)
        i++;
      if(array[0][0].length > 1)
        i++;
      return i;
    }
    @Override
    public void reachable(Heap h){
      for(NumVal[][] arr: array){
        for(NumVal[] ar: arr){
          for(NumVal v : ar){
            v.reachable(h);
          }
        }
      }
      h.mark(this, false);
    }
  }
	static class NumVal implements Value {
	    private double _val;
	    public NumVal(double v) { _val = v; }
	    public double v() { return _val; }
	    public String tostring() {
	    	int tmp = (int) _val;
	    	if(tmp == _val) return "" + tmp;
	    	return "" + _val;
	    }
      @Override
      public void reachable(Heap h){
        h.mark(this, false);
      }
	}
	static class BoolVal implements Value {
		private boolean _val;
	    public BoolVal(boolean v) { _val = v; }
	    public boolean v() { return _val; }
	    public String tostring() { if(_val) return "#t"; return "#f"; }
      @Override
      public void reachable(Heap h){
        h.mark(this, false);
      }
	}

	static class StringVal implements Value {
		private java.lang.String _val;
	    public StringVal(String v) { _val = v; }
	    public String v() { return _val; }
	    public java.lang.String tostring() { return "" + _val; }
      @Override
      public void reachable(Heap h){
        h.mark(this, false);
      }
	}
	static class PairVal implements Value {
		protected Value _fst;
		protected Value _snd;
	    public PairVal(Value fst, Value snd) { _fst = fst; _snd = snd; }
		public Value fst() { return _fst; }
		public Value snd() { return _snd; }
	    public java.lang.String tostring() {
	    	if(isList()) return listToString();
	    	return "(" + _fst.tostring() + " " + _snd.tostring() + ")";
	    }
	    boolean isList() {
	    	if(_snd instanceof Value.Null) return true;
	    	if(_snd instanceof Value.PairVal &&
	    		((Value.PairVal) _snd).isList()) return true;
	    	return false;
	    }
	    java.lang.String listToString() {
	    	String result = "(";
	    	result += _fst.tostring();
	    	Value next = _snd;
	    	while(!(next instanceof Value.Null)) {
	    		result += " " + ((PairVal) next)._fst.tostring();
	    		next = ((PairVal) next)._snd;
	    	}
	    	return result + ")";
	    }
    @Override
    public void reachable(Heap h){
      _fst.reachable(h);
      _snd.reachable(h);
      h.mark(this, false);
    }
	}
	static class Null implements Value {
		public Null() {}
	    public String tostring() { return "()"; }
    @Override
    public void reachable(Heap h){
      h.mark(this, false);
    }
	}
	static class UnitVal implements Value {
		public static final UnitVal v = new UnitVal();
	    public String tostring() { return ""; }
    @Override
    public void reachable(Heap h){
      h.mark(this, false);
    }
	}
	static class DynamicError implements Value {
		private String message = "Unknown dynamic error.";
		public DynamicError(String message) { this.message = message; }
	    public String tostring() { return "" + message; }
    @Override
    public void reachable(Heap h){
      h.mark(this, false);
    }
	}
}
