
function test1(){
  var matrix = [[0,0,1,0,0], [0,0,1,0,0], [0,0,0,1,0], [0,0,0,0,0], [0,0,0,0,0]];
  var new_matrix = [[0,0,0,0,0], [0,0,0,0,0], [0,0,0,0,0], [0,0,0,0,0], [0,0,0,0,0]];

  for(var i = 0; i < matrix.length; i++){
    for(var j =0; j < matrix.length; j++){
      if(matrix[i][j]){
        for(var k = 0; k < matrix.length; k++){
          if(matrix[j][k] && k != i){
            new_matrix[i][k] = 1;
          }
        }
      }
    }
  }

  console.log(matrix);
  console.log(new_matrix);
}

function test2(){
  var list = [
    [1],
    [2,4],
    [6,0,4],
    [0,2],
    [5],
    [2,6],
    [3]
  ];

  var from = [];

  for(var i = 0; i < list.length; i++){
    from[i] = 0;
  }

  for(var i = 0; i < list.length; i++){
    var adj = list[i];
    for(var j = 0; j < adj.length; j++){
      var adj2 = list[adj[j]];
      if(adj2.includes(i)){
        from[i] = -1;
        from[j] = -1;
      } else{
        if(from[adj[j]] > -1){
          from[adj[j]]++;
        }
      }
    }
  }

  for(var i = 0; i < list.length; i++){
    if(list[i].length == from[i] && 2*from[i] + 1 == list.length){
      return i;
    }
  }
}

console.log(test2());
