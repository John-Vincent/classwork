package reflang;

import java.util.ArrayList;
/**
 * Representation of a heap, which maps references to values.
 *
 * @author hridesh
 *
 */
public interface Heap {

	Value ref (Value value);

	Value deref (Value.RefVal loc);

  Value deref(Value.NumVal loc);

	Value setref (Value.RefVal loc, Value value);

  Value setref (Value.NumVal loc, Value value);

	Value free (Value.RefVal value);

  void mark(Value v, boolean t);

  void gc(Env e);

	static public class Heap16Bit implements Heap {
		static final int HEAP_SIZE = 3;

		Touple[] _rep = new Touple[HEAP_SIZE];
    FreeList _list = new FreeList();

		public Value ref (Value value) {
      int index;
      try{
        index = _list.alloc();
      } catch(IndexOutOfBoundsException e){
				return new Value.DynamicError("Out of memory error");
      }
			Value.RefVal new_loc = new Value.RefVal(index);
			_rep[index] = new Touple(value, false);
			return new_loc;
		}

		public Value deref (Value.RefVal loc) {
			try {
				return _rep[loc.loc()].getVal();
			} catch (ArrayIndexOutOfBoundsException e) {
				return new Value.DynamicError("Segmentation fault at access " + loc);
			}
		}

    public Value deref(Value.NumVal loc){
      try {
				return _rep[(int)loc.v()].getVal();
			} catch (ArrayIndexOutOfBoundsException e) {
				return new Value.DynamicError("Segmentation fault at access " + loc);
			}
    }

		public Value setref (Value.RefVal loc, Value value) {
			try {
				_rep[loc.loc()] = new Touple(value, false);
        return value;
			} catch (ArrayIndexOutOfBoundsException e) {
				return new Value.DynamicError("Segmentation fault at access " + loc);
			}
		}

    public Value setref (Value.NumVal loc, Value value) {
			try {
				_rep[(int)loc.v()] = new Touple(value, false);
        return value;
			} catch (ArrayIndexOutOfBoundsException e) {
				return new Value.DynamicError("Segmentation fault at access " + loc);
			}
		}

		public Value free (Value.RefVal loc) {
			try {
				_rep[loc.loc()] = null;
        _list.free(loc.loc());
				return null;
			} catch (ArrayIndexOutOfBoundsException e) {
				return new Value.DynamicError("Segmentation fault at access " + loc);
			}
		}

    public void gc(Env env){
      this.markAll();
      this.clearReachable(env);
      this.sweep();
    }

    public void sweep(){
      int j = 0;
      int s = _list.size();
      int[] h = {-1, -1};
      if(j < s)
        _list.get(j++,h);
      for(int i = 0; i < HEAP_SIZE; i++){
        if(h[0] == i){
          i = i + h[1];
          if(j < s)
            _list.get(j++,h);
        } else if(_rep[i].getFlag()){
          //System.out.println("removing " + _rep[i].getVal().tostring() + " from the heap");
          _rep[i] = null;
          _list.free(i);
        }
      }
      _list.coalesce();
    }

    public void markAll(){
      int j = 0;
      int s = _list.size();
      int[] h = {-1, -1};
      if(j < s)
        _list.get(j++,h);
      for(int i = 0; i < HEAP_SIZE; i++){
        if(h[0] == i){
          i = i + h[1];
          if(j < s)
            _list.get(j++,h);
        } else{
          _rep[i].setFlag(true);
        }
      }
    }

    @Override
    public void mark(Value v, boolean t){
      int j = 0;
      int s = _list.size();
      int[] h = {-1, -1};
      if(j < s)
        _list.get(j++,h);
      for(int i = 0; i < HEAP_SIZE; i++){
        if(h[0] == i){
          i = i + h[1];
          if(j < s)
            _list.get(j++,h);
        } else if(_rep[i].getVal() == v){
          //System.out.println("marking " + v.tostring() + " as in scope");
          _rep[i].setFlag(t);
        }
      }
    }

    public void clearReachable(Env e){
      e.computeReachable(this);
    }

    private class Touple {
      private Value v;
      private boolean flag;
      public Touple( Value v, Boolean b){
        this.v = v;
        this.flag = b;
      }
      public Value getVal(){
        return v;
      }
      public Boolean getFlag(){
        return flag;
      }
      public void setFlag(boolean f){
        this.flag = f;
      }
    }

    private class FreeList {
      ArrayList<Touple> list = new ArrayList<Touple>();
      public FreeList(){list.add(new Touple(0, HEAP_SIZE));}
      public int alloc(){
        Touple s = list.get(0);
        //System.out.println(s.index + " " + s.space);
        int ans;
        if(s.space > 1){
          ans = s.index;
          s.index++;
          s.space--;
        } else {
          ans = s.index;
          list.remove(0);
        }
        java.util.Collections.sort(list);
        return ans;
      }
      public void free(int i){
        list.add(new Touple(i, 1));
        java.util.Collections.sort(list);
      }
      public void get(int i, int[] a){
        Touple t = list.get(i);
        a[0] = t.index;
        a[1] = t.space;
      }
      public int size(){
        return list.size();
      }
      public void coalesce(){
        Touple a, b;
        ArrayList<Touple> n = new ArrayList<Touple>();
        if(list.size() == 1)
          return;
        for(int i = 0; i < list.size()-1; i++){
          a = list.get(i);
          b = list.get(i+1);
          if(a.index+a.space >= b.index){
            n.add(new Touple(a.index, b.index+b.space-a.index));
            //System.out.println(a.index+ " " +  (b.index + b.space-a.index));
          }else{
            n.add(a);
            //System.out.println(a.index + " " + a.space);
          }
        }
        this.list = n;
      }
      private class Touple implements Comparable<Touple>{
        public int index;
        public int space;
        public Touple(int i, int s){
          this.index = i;
          this.space = s;
        }
        public int compareTo(Touple e){
          return this.index - e.index;
        }
      }
    }

		public Heap16Bit(){}
	}

}
