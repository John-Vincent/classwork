(define leaf
  (lambda (leafval)
    (lambda (x)
      (if (= x 0)
        leafval
        (list)
      )
    )
  )
)

(define interior
  (lambda (rootval lefttree righttree)
    (lambda (x)
      (if (= x 0)
        rootval
        (if (= x 1)
          lefttree
          righttree
        )
      )
    )
  )
)

(define traverse
  (lambda (tree op combine)
    (if (null? (tree 1))
      (op (tree 0))
      (combine (op (tree 0)) (traverse (tree 1) op combine) (traverse (tree 2) op combine))
    )
  )
)
