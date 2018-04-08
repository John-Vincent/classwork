(define empty_dict
  (lambda () "!empty-dict!")
)

(define empty_dictQ
  (lambda (d)
    (if (= d "!empty-dict!")
      #t
      #f
    )
  )
)

(define look
  (lambda (w1 d1)
    (if (empty_dictQ d1)
      (list)
      (if (= (car d1) w1)
        (cons (car (cdr d1)) (look w1 (cdr (cdr d1))))
        (look w1 (cdr (cdr d1)))
      )
    )
  )
)

(define add
  (lambda (w m d1)
    (cons w (cons m d1))
  )
)

(define pd (add "penis" "what matt eats" (empty_dict)))

(define md (add "matt" "guy who eats cock" pd))

(define ppd (add "penis" "what matt butt chugs" md))
