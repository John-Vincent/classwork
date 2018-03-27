//Q3
(define program8
  (lambda ()
    (let ((x (ref 2)))
      (if
        (=
          (let ((y (ref 1)))
            (deref x)
          )
          2
        )
        (let ((y (ref 6)))
          (let ((q (ref 4)))
            (deref q)
          )
        )
        4
      )
    )
  )
)
