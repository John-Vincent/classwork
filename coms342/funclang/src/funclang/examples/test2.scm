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

//9

(define sumseries
  (lambda (x)
    (sumplus 2 x)
  )
)
(define sumplus
  (lambda (num x)
    (if (= x 0)
      0
      (+ (/ 1 num) (summinus (+ num num) (- x 1)))
    )
  )
)
(define summinus
  (lambda (num x)
    (if (= x 0)
      0
      (- (sumplus (+ num num) (- x 1)) (/ 1 num))
    )
  )
)

//10
(define list342
  (list (list 3) (list 4) (list 2))
)

(define extract4
  (lambda ()
    (car ( cdr list342 ))
  )
)

(define extract2
  (lambda ()
    (car ( cdr (cdr list342) ))
  )
)

(define append541
  (lambda ()
    (list (car list342) (car (cdr list342)) (car (cdr (cdr list342))) 5 4 1)
  )
)

(define checkEmpty
  (lambda ()
    (null? (cdr (extract2)))
  )
)

//11
(define frequency
  (lambda  (lst elem)
    (if (null? lst)
      0
      (if (= elem (car lst))
        (+ 1 (frequency (cdr lst) elem))
        (frequency lst elem)
      )
    )
  )
)

//12
(define max
  (lambda (lst)
    (if (null? lst)
      0
      (if (> (car lst) (max (cdr lst)))
        (car lst)
        (max (cdr lst))
      )
    )
  )
)

//13
(define min
  (lambda (lst)
    (if (null? lst)
      0
      (if (< (car lst) (max (cdr lst)))
        (car lst)
        (max (cdr lst))
      )
    )
  )
)

//14
(define andOfList
  (lambda (lst)
    (if (null? lst)
      #t
      (if (car lst)
        (andOfList (cdr lst))
        #f
      )
    )
  )
)

//15
(define orOfList
  (lambda (lst)
    (if (null? lst)
      #f
      (if (car lst)
        #t
        (orOfList (cdr lst))
      )
    )
  )
)

//16
(define average
  (lambda (lst)
    (if (null? lst)
      0
      (/ (sum lst) (count lst))
    )
  )
)
(define sum
  (lambda (lst)
    (if (null? lst)
      0
      (+ (car lst) (sum (cdr lst)))
    )
  )
)
(define count
  (lambda (lst)
    (if (null? lst)
      0
      (+ 1 (count (cdr lst)))
    )
  )
)

//17
(define alternate
	(lambda (lst1 lst2)
		(if (null? lst1) lst2
			(if (null? lst2) lst1
				(cons (car lst1) (alternate lst2 (cdr lst1)))
			)
		)
	)
)

//18
(define hypotenuse
  (lambda (x)
    (lambda (y)
      (+ (* x x) (* y y))
    )
  )
)
