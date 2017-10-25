a = [10,24,-32,6,12,-3,81,-40,-2,8,16]

class Span:
    start = 0
    end = 0
    summ = 0


def maxSubArraySum(a):

    max_so_far = Span()
    curr_max = Span()

    for i in range(1,len(a)):
        if a[i] > curr_max.summ + a[i]:
            curr_max.summ = a[i]
            curr_max.start = i
        else:
            curr_max.summ += a[i]
            curr_max.end = i
        if max_so_far.summ < curr_max.summ:
            max_so_far.summ = curr_max.summ
            max_so_far.start = curr_max.start
            max_so_far.end = curr_max.end
    return max_so_far


ans = maxSubArraySum(a)
print(ans.summ, ans.start, ans.end)
