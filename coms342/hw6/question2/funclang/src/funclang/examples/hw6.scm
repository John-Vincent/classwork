(define repeated
  (lambda (f a)
    (lambda (d)
      ( __repeat__ f a d)
    )
  )
)

(define __repeat__
  (lambda (f x y)
    (if (= x 0)
      y
      ( __repeat__ f (- x 1) (f y) )
    )
  )
)
