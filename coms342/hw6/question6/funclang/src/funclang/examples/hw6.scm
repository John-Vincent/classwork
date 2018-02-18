(define emptyenv
  (lambda ()
    (lambda (x)
      "undefined"
    )
  )
)

(define extendenv
  (lambda (i v e)
    (lambda (x)
      (if (= x i)
        v
        (e x)
      )
    )
  )
)

(define get
  (lambda (i e)
    (e i)
  )
)
