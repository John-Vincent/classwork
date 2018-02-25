(define odd
	(lambda (n)
		(if (= n 0) #f
			(if (even (- n 1)) #t
				#f
			)
		)
	)
)

(define even
	(lambda (n)
		(if (= n 0) #t
			(if (odd (- n 1)) #t
				#f
			)
		)
	)
)

(define append
	(lambda (lst1 lst2)
		(if (null? lst1) lst2
			(if (null? lst2) lst1
				(cons (car lst1) (append (cdr lst1) lst2))
			)
		)
	)
)


//QUESTION 1
(define carpet
  (lambda (x)
    (if (odd x)
      (__carpet__ 0 x #t)
      (__carpet__ 0 x #f)
    )
  )
)


(define __carpet__
  (lambda (r c n)
    (if (< r (+ 1 (* c 2)))
      (cons (__carpet_row__ r c n) (__carpet__ (+ r 1) c n))
      (list)
    )
  )
)

(define __carpet_row__
  (lambda (r c n)
    (if (= r 0)
      (if n
        (fill 1 (+ 1 (* c 2)) (list))
        (fill 0 (+ 1 (* c 2)) (list))
      )
      (if (= r (* c 2) )
        (if n
          (fill 1 (+ 1 (* c 2)) (list))
          (fill 0 (+ 1 (* c 2)) (list))
        )
        (if n
          (cons 1 (append (__carpet_row__ (- r 1) (- c 1) #f) (list 1)) )
          (cons 0 (append (__carpet_row__ (- r 1) (- c 1) #t) (list 0)) )
        )
      )
    )
  )
)

(define fill
  (lambda (v t l)
    (if (< t 2)
      (cons v l)
      (cons v (fill v (- t 1) l))
    )
  )
)


//QUESTION 2
(define pascal
  (lambda (n)
    (__pascal__ n 1)
  )
)

(define __pascal__
  (lambda (n x)
    (if (= (+ 1 n) x)
      (list)
      (cons (__pascal_row__ (- x 1) 0) (__pascal__ n (+ x 1)))
    )
  )
)

(define __pascal_row__
  (lambda (n x)
    (if (= n x)
      (list 1)
      (cons (combination n x) (__pascal_row__ n (+ x 1)))
    )
  )
)

(define combination
  (lambda (n r)
    (/ (factorial n) (* (factorial r) (factorial (- n r))))
  )
)


(define factorial
  (lambda (n)
    (if (> n 1)
      (* n (factorial (- n 1)))
      1
    )
  )
)


//QUESTION 3

//part a
(define seq
  (lambda (s1 s2)
    (lambda (x)
      (if (= x 4)
        #t
        (if (= x 5)
          s1
          (if (= x 6)
            s2
            #f
          )
        )
      )
    )
  )
)

(define up
  (lambda (v)
    (lambda (t)
      (if (= t 0)
        #t
        (if (= t 10)
          v
          #f
        )
      )
    )
  )
)

(define down
  (lambda (v)
    (lambda (t)
      (if (= t 1)
        #t
        (if (= t 10)
          (- 0 v)
          #f
        )
      )
    )
  )
)

(define right
  (lambda (v)
    (lambda (t)
      (if (= t 2)
        #t
        (if (= t 10)
          v
          #f
        )
      )
    )
  )
)

(define left
  (lambda (v)
    (lambda (t)
      (if (= t 3)
        #t
        (if (= t 10)
          (- 0 v)
          #f
        )
      )
    )
  )
)

//part b
(define isseq
  (lambda (x)
    (x 4)
  )
)

(define isup
  (lambda (x)
    (x 0)
  )
)

(define isdown
  (lambda (x)
    (x 1)
  )
)

(define isright
  (lambda (x)
    (x 2)
  )
)

(define isleft
  (lambda (x)
    (x 3)
  )
)

//part c
(define seqfst
  (lambda (x)
    (x 5)
  )
)

(define seqsnd
  (lambda (x)
    (x 6)
  )
)

(define upvalue
  (lambda (x)
    (x 10)
  )
)

(define downvalue
  (lambda (x)
    (x 10)
  )
)

(define rightvalue
  (lambda (x)
    (x 10)
  )
)

(define leftvalue
  (lambda (x)
    (x 10)
  )
)

//part d
(define move
  (lambda (p s)
    (if (s 4)
      (move (move p (s 5)) (s 6))
      (if (s 2)
        (list (+ (car p) (s 10)) (car (cdr p)))
        (if (s 3)
          (list (+ (car p) (s 10)) (car (cdr p)))
          (list (car p) (+ (car (cdr p)) (s 10)))
        )
      )
    )
  )
)

//QUESTION 4



//QUESTION 5
(define inc (lambda (x) (+ x 1)))
(define inc2 (lambda (x) (+ x 2)))
(define test25 (lambda (x) (< x 25)))
(define test42 (lambda (x) (< x 42)))
(define body (lambda (x) (+ x 42)))

(define forloop
  (lambda (x test inc)
    (lambda (body)
      (if (test x)
        (__loop__ (inc x) test inc body (body x))
        -1
      )
    )
  )
)

(define __loop__
  (lambda (x test inc body return)
    (if (test x)
      (__loop__ (inc x) test inc body (body x))
      return
    )
  )
)
