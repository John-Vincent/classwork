(define board
  (lambda (n sym1 sym2)
    (__alt2__ n n sym1 sym2)
  )
)

(define __alt__
  (lambda (n sym1 sym2)
    (if (= n 0)
      (list)
      (cons sym1 (__alt__ (- n 1) sym2 sym1))
    )
  )
)

(define __alt2__
  (lambda (x n sym1 sym2)
    (if (= n 0)
      (list)
      (cons (__alt__ x sym1 sym2) (__alt2__ x (- n 1) sym2 sym1))
    )
  )
)
