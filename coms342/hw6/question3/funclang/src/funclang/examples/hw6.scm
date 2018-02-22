(define smooth
  (lambda (f dx)
    (lambda (x)
      (/ (+ (f x) (f (+ x dx)) (f (- x dx))) 3)
    )
  )
)
