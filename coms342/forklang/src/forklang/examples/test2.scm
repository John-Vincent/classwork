(define empty_dict
  (lambda () (lambda (t) "!empty-dict!"))
)

(define empty_dictQ
  (lambda (d)
    (if (= (d 1) "!empty-dict!")
      #t
      #f
    )
  )
)

(define look
  (lambda (w1 d1)
    (if (empty_dictQ d1)
      (list)
      (if (= (d1 0) w1)
        (cons (d1 1) (look w1 (d1 2)))
        (look w1 (d1 2))
      )
    )
  )
)

(define add
  (lambda (w m d1)
    (lambda (t)
      (if (= t 0)
        w
        (if (= t 1)
          m
          d1
        )
      )
    )
  )
)

(define pd (add "penis" "what matt eats" (empty_dict)))

(define md (add "matt" "guy who eats cock" pd))

(define ppd (add "penis" "what matt butt chugs" md))
