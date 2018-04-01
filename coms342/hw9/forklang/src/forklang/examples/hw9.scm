//question 1
(define seq (lambda (f s) s))
(define example1 (lambda ()
    (let
        ((x (ref 0)))
        (seq
            (fork
              (let ((e1 (lock x)) (e2 (set! x (+ 1 (deref x))))) (unlock x))
	            (let ((e1 (lock x)) (e2 (set! x (+ 1 (deref x))))) (unlock x))
            )
            (deref x)
        )
    )
))


//question 2
(define bankaccount
	 (lambda (balanceref)
	 	(lambda (op)
	 		(if op
	 			balanceref
	 			(deref balanceref)
	 		)
	 	)
	 )
)

(define account
   	(lambda (x)
         (bankaccount (ref x))
   	)
)

(define getbalance
	(lambda (acc)
		(seq3 (lock (acc #t)) (acc #f) (unlock (acc #t)))
	)
)

(define withdraw
	(lambda (acc amount)
    (seq3
      (lock (acc #t))
      (set!
  			(acc #t)
  			(- (acc #f) amount)
  		)
      (unlock (acc #t))
    )
	)
)

(define deposit
	(lambda (acc amount)
    (seq3
      (lock (acc #t))
      (set!
        (acc #t)
        (+ (acc #f) amount)
      )
      (unlock (acc #t))
    )
	)
)

// Following two accounts help us test this program
(define A (account 200))
(define B (account 200))

(define seq2 (lambda (f1 f2) f2))
(define seq3 (lambda (f1 f2 f3) f2))
(define seq4 (lambda (f1 f2 f3 f4) f4))
(define banktest
	(lambda ()
		(seq2
			(fork
				(seq4 (withdraw A 99) (withdraw B 99) (deposit A 99) (deposit B 99))
				(seq4 (withdraw B 101) (withdraw A 101) (deposit B 101) (deposit A 101))
			)
			(list (getbalance A) (getbalance B))
		)
	)
)


(define resource1 (ref 42))
(define resource2 (ref 342))

(define factorial
   (lambda (n)
   	(if (= n 0) 1
   		(* n (factorial (- n 1)))
   	)
   )
)

(define lock12
	(lambda (f arg)
		(let
			((l1 (lock resource1))
			 (l2 (lock resource2))
			 (result (f arg))
			 (ul1 (unlock resource1))
			 (ul2 (unlock resource2)))
			result
		)
	)
)

(define lock21
	(lambda (f arg)
		(let
			((l1 (lock resource2))
			 (l2 (lock resource1))
			 (result (f arg))
			 (ul1 (unlock resource2))
			 (ul2 (unlock resource1)))
			result
		)
	)
)

(define lockit
	(lambda ()
		(fork
			(factorial 2)
			(factorial 3)
		)
	)
)
