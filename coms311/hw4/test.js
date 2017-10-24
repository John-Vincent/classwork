Node = function(val){
  this.value = val;
  this.compare = null;
  this.parent = null;
  this.next = null;
}

//A = [27,1,2,3,51,5,6,7,8,0];
//calc();
k = 3
a = [1, 32, 12, 43, 53, 23, 64, 65, 34, 91, 66, 45, 100, 72, 56, 112];

n = a.length;
sort();

function calc(){
  n1 = null;
  n2 = new Node(A[0]);
  for(var i = 1; i < A.length; i++){
    n1 = new Node(A[i]);
    n1.next = n2;
    n2 = n1;
  }
  n3 = n4 = null;
  newLevel = false;
  //makes a graph of linked nodes in the shape of a pyramid, nodes on a level are linked one direction through the next pointer
  //all nodes that get compared are linked, the larger value gets a clone in the next level with a pointer to it
  // will complete after log_2 n levels are formed with the last level, ends when it starts on a level with only 1 element
  while(!newLevel || n2.next){
    newLevel = false;
    n1 = n2.next;
    if(n1){
      n2.compare = n1;
      n1.compare = n2;
      if(n1.value > n2.value){
        n3 = new Node(n1.value);
        n3.parent = n1
      }else{
        n3 = new Node(n2.value);
        n3.parent = n2;
      }
      n3.next = n4;
      n4 = n3;
      if(n1.next){
        n2 = n1.next;
      }else{
        n4 = null;
        n2 = n3;
        newLevel = true;
      }
    } else{
      newLevel = true;
      n3 = new Node(n2.value);
      n3.parent = n2;
      n3.next = n4;
      n4 = null;
      n2 = n3;
    }
  }
  largest = n2.value
  n2 = n2.parent;
  second = n2.compare.value;
  while(n2.parent){
    n2 = n2.parent;
    if(n2.compare && n2.compare.value > second){
      second = n2.compare.value;
    }
  }
  console.log(largest);
  console.log(second);
}

function sort(){
  b = Array(n);
  c = Array(k);
  smallest = null;
  sub = 0;
  for(i = 0; i < k; i++){
    c[i] = 0;
  }
  for(i = 0; i < n; i++){
    smallest = a[c[0]*k];
    sub = 0;
    for(j = 1; j < k; j++){
      var val = c[j]*k+j
      if(val < a.length && a[val] < smallest){
        smallest = a[val];
        sub = j;
      }
    }
    c[sub]++;
    b[i] = smallest;
  }
  console.log(b);
}
