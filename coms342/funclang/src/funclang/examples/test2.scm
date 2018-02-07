//8
(define isRightAngled
  (lambda (height base hyp)
    (if
      (=
        (* hyp hyp)
        (+ (* base base) (* height height))
      )
      #t
      #f
    )
  )
)

//10
(define list342
  (list (list 3) (list 4) (list 2))
)

(define extract4
  (lambda (l)
    (car ( cdr l ))
  )
)
