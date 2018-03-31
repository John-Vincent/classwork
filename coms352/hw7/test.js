var pages = [1, 2, 3, 4, 2, 1, 5, 6, 2, 1, 2, 3, 7, 6, 3, 2, 1, 2, 3, 6];

function LRU(frames){
  var p = [];
  var set = false;
  var replace = 0;
  var ans = 0;
  for(var i = 0; i < pages.length; i++){
    for(var j = 0; j < frames; j++){
      if(p[j] != undefined && p[j].page == pages[i]){
        p[j].time = i;
        set = true;
      }
      if(p[j] == undefined || (p[replace] != undefined && p[j].time < p[replace].time) || j == 0){
        replace = j;
      }
    }
    if(!set){
      p[replace] = {page: pages[i], time: i};
      ans++;
    }
    set = false;
    replace = 0;
  }
  console.log("LRU = " + ans);
}

function FIFO(frames){
  var p = [];
  var set = false;
  var replace = 0;
  var ans = 0;
  for(var i = 0; i < pages.length; i++){
    for(var j = 0; j < frames; j++){
      if(p[j] != undefined && p[j].page == pages[i]){
        set = true;
      }
      if(p[j] == undefined || (p[replace] != undefined && p[j].time < p[replace].time) || j == 0){
        replace = j;
      }
    }
    if(!set){
      p[replace] = {page: pages[i], time: i};
      ans++;
    }
    set = false;
    replace = 0;
  }
  console.log("FIFO = " + ans);
}


for(var i = 1; i < 8; i++){
  LRU(i);
  FIFO(i);
}
