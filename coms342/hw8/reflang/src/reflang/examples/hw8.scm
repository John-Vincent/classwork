//Q1 a
(define program1 (lambda () (deref (ref 1)) ))
(define program2 (lambda () (free (ref 1)) ))
(define program3 (lambda () (let ((loc (ref 1))) (set! loc 2)) ))
(define program4 (lambda () (let ((loc (ref 3))) (set! loc (deref loc))) ))

//Q1 b
(define program5
  (lambda ()
    (let ((x (ref 2)))
      (let ((y x))
        (if (= (set! y 5) 5)
          (deref x)
          (deref x)
        )
      )
    )
  )
)

(define program6
  (lambda ()
    (let ((x (ref 34)))
      (let ((y x))
        (= (deref x) (deref y))
      )
    )
  )
)

(define program7
  (lambda ()
    (let ((x (ref (ref 2))))
      (let ((y (ref (deref x)) ))
        (if (= (set! (deref x) 100) 100)
          (deref (deref y))
          (deref (deref y))
        )
      )
    )
  )
)
