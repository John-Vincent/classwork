var obs = [
    {point: [2,3,0], value: "Red"},
    {point: [2,0,1], value: "Red"},
    {point: [0,1,3], value: "Red"},
    {point: [0,1,2], value: "Green"},
    {point: [-1,0,1], value: "Green"},
    {point: [1,-1,1], value: "Red"}
];

var point = [0,0,0];

console.log(knn(process.argv[2], point, obs));

//k is number of neighbors
function knn(k, point, obs)
{
    var sum, i, j, max, value;
    var ordered_list = [], num_neighbors = {};
    var answer;

    for(i = 0; i < obs.length; i++)
    {
        sum = 0;
        for(j = 0; j < point.length; j++)
        {
            sum += Math.pow(obs[i].point[j] - point[j], 2);
        }
        sum = Math.sqrt(sum);
        for(j = 0; j < ordered_list.length; j++)
        {
            if(ordered_list[j].dist >= sum)
                break;
        }
        ordered_list.splice(j, 0, {dist: sum, obs: obs[i]});
    }
    
    max = 0;
    for(i = 0; i < k; i++)
    {
        value = ordered_list[i].obs.value;
        if(num_neighbors[value])
            num_neighbors[value]++;
        else
            num_neighbors[value] = 1;

        if(num_neighbors[value] > max)
        {
            answer = value;
            max = num_neighbors[value];
        }
    }
    return answer;
}
