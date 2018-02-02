package varlang;

public interface Value {
	public String toString();
	static class NumVal implements Value {
		private double _val;
	    public NumVal(double v) { _val = v; }
	    public double v() { return _val; }
	    public String toString() {
	    	int tmp = (int) _val;
	    	if(tmp == _val) return "" + tmp;
	    	return "" + _val;
	    }
	}
  static class DynamicError implements Value {
    String s;
    public DynamicError(String s){
      this.s = s;
    }

    public void append(DynamicError d){
      this.s += "\n" + d.s;
    }
    public String toString(){
      return s;
    }
  }
}
