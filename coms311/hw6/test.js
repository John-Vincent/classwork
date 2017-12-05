

a = [-1, 4, -3];

for(i = 0; i < 10; i++){
  a[i] = [];
  for(j = 0; j < 10; j++){
   a[i][j] = Math.round(Math.random() *100 -90);
   if(a[i][j] < 0){
     a[i][j] = 0
   }
  }
}//*/

console.log(a);

console.log(getDiamonds(0, 0, 10, 10, a));

//console.log(subSum(a));

function getDiamonds(x, y, ex, ey, a){
  var table = [];
  var max = 0;
  for(i = 0; i < ex-x; i++){
    table[i] = [];
    for(j = 0; j < ey-y; j++){
      max = 0;
      if( i == 0 && j == 0){
        table[i][j] = a[i+x][j+y];
      }else{
        if(i > 0 && j > 0){
          max = table[i-1][j-1] - 3;
          if( max < table[i-1][j] -2){
            max = table[i-1][j] - 2;
          }
          if( max < table[i][j-1] -2){
            max = table[i][j-1] - 2;
          }
        }else if( i > 0){
          max = table[i-1][j] - 2;
        } else{
          max = table[i][j-1] - 2;
        }
        table[i][j] = max + a[i+x][j+y];
      }
    }
  }
  console.log(table);
  return table[ex-x-1][ey-y-1];
}

function subSum(A){
  var sum = 0;
  var table = [];
  var min =0;
  var max = 0;
  for ( i = 0; i < A.length; i++){
    sum += A[i];
    table[i] = [];
    if(A[i] < 0){
      min += A[i];
    } else{
      max += A[i];
    }
  }
  if(min > 0){
    min = 0;
  }
  if(max < 0){
    max = 0;
  }

  table[A.length] = [];
  sum /= 2;
  console.log(sum, min, max);

  for(i = 0; i <= A.length; i++){
    for(j = min; j<= max; j++){
      if(i == 0){
        table[i][j-min] = (j == 0);
      } else if(table[i-1][j - min]){
        table[i][j-min] = true;
      } else{
        table[i][j-min] = A[i-1] == j || (j-A[i-1] >= min && j-A[i-1] <= max && table[i-1][j-A[i-1] - min] == true);
      }
    }
    console.log(table[i]);
  }
  return table[A.length][sum - min] == true;
}
