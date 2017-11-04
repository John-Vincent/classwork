
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
    []
  ]
}
