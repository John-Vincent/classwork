grammar ForkLang;

 program returns [Program ast]
 		locals [ArrayList<DefineDecl> defs, Exp expr]
 		@init { $defs = new ArrayList<DefineDecl>(); $expr = new UnitExp(); } :
		(def=definedecl { $defs.add($def.ast); } )* (e=exp { $expr = $e.ast; } )?
		{ $ast = new Program($defs, $expr); }
		;

 definedecl returns [DefineDecl ast] :
 		'(' Define
 			id=Identifier
 			e=exp
 		')' { $ast = new DefineDecl($id.text, $e.ast); }
 		;

exp returns [Exp ast]:
		va=varexp { $ast = $va.ast; }
		| num=numexp { $ast = $num.ast; }
		| str=strexp { $ast = $str.ast; }
		| bl=boolexp { $ast = $bl.ast; }
        | add=addexp { $ast = $add.ast; }
        | sub=subexp { $ast = $sub.ast; }
        | mul=multexp { $ast = $mul.ast; }
        | div=divexp { $ast = $div.ast; }
        | let=letexp { $ast = $let.ast; }
        | lam=lambdaexp { $ast = $lam.ast; }
        | call=callexp { $ast = $call.ast; }
        | i=ifexp { $ast = $i.ast; }
        | less=lessexp { $ast = $less.ast; }
        | eq=equalexp { $ast = $eq.ast; }
        | gt=greaterexp { $ast = $gt.ast; }
        | car=carexp { $ast = $car.ast; }
        | cdr=cdrexp { $ast = $cdr.ast; }
        | cons=consexp { $ast = $cons.ast; }
        | list=listexp { $ast = $list.ast; }
        | nl=nullexp { $ast = $nl.ast; }
        | lrec=letrecexp { $ast = $lrec.ast; }
        | ref=refexp { $ast = $ref.ast; }
        | deref=derefexp { $ast = $deref.ast; }
        | assign=assignexp { $ast = $assign.ast; }
        | free=freeexp { $ast = $free.ast; }
 // Begin: New Expressions for ForkLang
        | fork=forkexp { $ast = $fork.ast; }
        | lock=lockexp { $ast = $lock.ast; }
        | ulock=unlockexp { $ast = $ulock.ast; }
        | syn=syncexp {$ast = $syn.ast;}
        | nlock=nlockexp {$ast = $nlock.ast;}
 // End: New Expressions for ForkLang
        ;

 // Begin: New Expressions for ForkLang
 syncexp returns [SyncExp ast] :
        '(' 'synchronized'
              e1=exp
              e2=exp
        ')'  {$ast = new SyncExp($e1.ast, $e2.ast);}
        ;

  nlockexp returns [LockExp ast] :
        '(' 'lock?'
            e1=exp
        ')' {$ast = new LockExp($e1.ast, false);}
        ;

 forkexp returns [ForkExp ast] :
        '(' Fork
            e1=exp
            e2=exp
        ')' { $ast = new ForkExp($e1.ast, $e2.ast); }
         ;

 lockexp returns [LockExp ast] :
        '(' Lock
            e1=exp
        ')' { $ast = new LockExp($e1.ast, true); }
         ;

 unlockexp returns [UnlockExp ast] :
        '(' UnLock
            e1=exp
        ')' { $ast = new UnlockExp($e1.ast); }
         ;
// End: New Expressions for ForkLang

  refexp returns [RefExp ast] :
        '(' Ref
            e=exp
        ')' { $ast = new RefExp($e.ast); }
        ;

 derefexp returns [DerefExp ast] :
        '(' Deref
            e=exp
        ')' { $ast = new DerefExp($e.ast); }
        ;

 assignexp returns [AssignExp ast] :
        '(' Assign
            e1=exp
            e2=exp
        ')' { $ast = new AssignExp($e1.ast, $e2.ast); }
        ;

 freeexp returns [FreeExp ast] :
        '(' Free
            e=exp
         ')' { $ast = new FreeExp($e.ast); }
        ;

 letrecexp returns [LetrecExp ast]
        locals [ArrayList<String> ids = new ArrayList<String>(), ArrayList<Exp> funs = new ArrayList<Exp>(); ] :
 		'(' Letrec
 			'(' ( '(' id=Identifier fun=exp ')' { $ids.add($id.text); $funs.add($fun.ast); } )+  ')'
 			body=exp
 		')' { $ast = new LetrecExp($ids, $funs, $body.ast); }
 		;

 // New Expressions for FuncLang

 lambdaexp returns [LambdaExp ast]
        locals [ArrayList<String> formals ]
 		@init { $formals = new ArrayList<String>(); } :
 		'(' Lambda
 			'(' (id=Identifier { $formals.add($id.text); } )* ')'
 			body=exp
 		')' { $ast = new LambdaExp($formals, $body.ast); }
 		;

 callexp returns [CallExp ast]
        locals [ArrayList<Exp> arguments = new ArrayList<Exp>();  ] :
 		'(' f=exp
 			( e=exp { $arguments.add($e.ast); } )*
 		')' { $ast = new CallExp($f.ast,$arguments); }
 		;

 ifexp returns [IfExp ast] :
 		'(' If
 		    e1=exp
 			e2=exp
 			e3=exp
 		')' { $ast = new IfExp($e1.ast,$e2.ast,$e3.ast); }
 		;

 lessexp returns [LessExp ast] :
 		'(' Less
 		    e1=exp
 			e2=exp
 		')' { $ast = new LessExp($e1.ast,$e2.ast); }
 		;

 equalexp returns [EqualExp ast] :
 		'(' Equal
 		    e1=exp
 			e2=exp
 		')' { $ast = new EqualExp($e1.ast,$e2.ast); }
 		;

 greaterexp returns [GreaterExp ast] :
 		'(' Greater
 		    e1=exp
 			e2=exp
 		')' { $ast = new GreaterExp($e1.ast,$e2.ast); }
 		;

 // Predicates for each type of value

 isnumberexp returns [IsNumberExp ast] :
        '(' 'number?'
            e=exp
        ')' { $ast = new IsNumberExp($e.ast); }
        ;

 isbooleanexp returns [IsBooleanExp ast] :
        '(' 'boolean?'
            e=exp
        ')' { $ast = new IsBooleanExp($e.ast); }
        ;

 isstringexp returns [IsStringExp ast] :
        '(' 'string?'
            e=exp
        ')' { $ast = new IsStringExp($e.ast); }
        ;

 islistexp returns [IsListExp ast] :
        '(' 'list?'
            e=exp
        ')' { $ast = new IsListExp($e.ast); }
        ;

 ispairexp returns [IsPairExp ast] :
        '(' 'pair?'
            e=exp
        ')' { $ast = new IsPairExp($e.ast); }
        ;

 isunitexp returns [IsUnitExp ast] :
        '(' 'unit?'
            e=exp
        ')' { $ast = new IsUnitExp($e.ast); }
        ;

 isprocedureexp returns [IsProcedureExp ast] :
        '(' 'procedure?'
            e=exp
        ')' { $ast = new IsProcedureExp($e.ast); }
        ;

 isnullexp returns [IsNullExp ast] :
        '(' 'null?'
            e=exp
        ')' { $ast = new IsNullExp($e.ast); }
        ;

// Expressions related to list

 carexp returns [CarExp ast] :
 		'(' Car
 		    e=exp
 		')' { $ast = new CarExp($e.ast); }
 		;

 cdrexp returns [CdrExp ast] :
 		'(' Cdr
 		    e=exp
 		')' { $ast = new CdrExp($e.ast); }
 		;

 consexp returns [ConsExp ast] :
 		'(' Cons
 		    e1=exp
 			e2=exp
 		')' { $ast = new ConsExp($e1.ast,$e2.ast); }
 		;

 listexp returns [ListExp ast]
        locals [ArrayList<Exp> list]
 		@init { $list = new ArrayList<Exp>(); } :
 		'(' List
 		    ( e=exp { $list.add($e.ast); } )*
 		')' { $ast = new ListExp($list); }
 		;

 nullexp returns [NullExp ast] :
 		'(' Null
 		    e=exp
 		')' { $ast = new NullExp($e.ast); }
 		;

 strexp returns [StrExp ast] :
 		s=StrLiteral { $ast = new StrExp($s.text); }
 		;

 boolexp returns [BoolExp ast] :
 		TrueLiteral { $ast = new BoolExp(true); }
 		| FalseLiteral { $ast = new BoolExp(false); }
 		;

 // Other Standard Expressions

  numexp returns [NumExp ast]:
 		n0=Number { $ast = new NumExp(Integer.parseInt($n0.text)); }
  		| '-' n0=Number { $ast = new NumExp(-Integer.parseInt($n0.text)); }
  		| n0=Number Dot n1=Number { $ast = new NumExp(Double.parseDouble($n0.text+"."+$n1.text)); }
  		| '-' n0=Number Dot n1=Number { $ast = new NumExp(Double.parseDouble("-" + $n0.text+"."+$n1.text)); }
  		;

 addexp returns [AddExp ast]
        locals [ArrayList<Exp> list]
 		@init { $list = new ArrayList<Exp>(); } :
 		'(' '+'
 		    e=exp { $list.add($e.ast); }
 		    ( e=exp { $list.add($e.ast); } )+
 		')' { $ast = new AddExp($list); }
 		;

 subexp returns [SubExp ast]
        locals [ArrayList<Exp> list]
 		@init { $list = new ArrayList<Exp>(); } :
 		'(' '-'
 		    e=exp { $list.add($e.ast); }
 		    ( e=exp { $list.add($e.ast); } )+
 		')' { $ast = new SubExp($list); }
 		;

 multexp returns [MultExp ast]
        locals [ArrayList<Exp> list]
 		@init { $list = new ArrayList<Exp>(); } :
 		'(' '*'
 		    e=exp { $list.add($e.ast); }
 		    ( e=exp { $list.add($e.ast); } )+
 		')' { $ast = new MultExp($list); }
 		;

 divexp returns [DivExp ast]
        locals [ArrayList<Exp> list]
 		@init { $list = new ArrayList<Exp>(); } :
 		'(' '/'
 		    e=exp { $list.add($e.ast); }
 		    ( e=exp { $list.add($e.ast); } )+
 		')' { $ast = new DivExp($list); }
 		;

 varexp returns [VarExp ast]:
 		id=Identifier { $ast = new VarExp($id.text); }
 		;

 letexp  returns [LetExp ast]
        locals [ArrayList<String> names, ArrayList<Exp> value_exps]
 		@init { $names = new ArrayList<String>(); $value_exps = new ArrayList<Exp>(); } :
 		'(' Let
 			'(' ( '(' id=Identifier e=exp ')' { $names.add($id.text); $value_exps.add($e.ast); } )+  ')'
 			body=exp
 			')' { $ast = new LetExp($names, $value_exps, $body.ast); }
 		;

 // Lexical Specification of this Programming Language
 //  - lexical specification rules start with uppercase

 Define : 'define' ;
 Let : 'let' ;
 Dot : '.' ;
 Letrec : 'letrec' ;
 Lambda : 'lambda' ;
 If : 'if' ;
 Car : 'car' ;
 Cdr : 'cdr' ;
 Cons : 'cons' ;
 List : 'list' ;
 Null : 'null?' ;
 Less : '<' ;
 Equal : '=' ;
 Greater : '>' ;
 TrueLiteral : '#t' ;
 FalseLiteral : '#f' ;
 Ref : 'ref' ;
 Deref : 'deref' ;
 Assign : 'set!' ;
 Free : 'free' ;
 Fork : 'fork' ;
 Lock : 'lock' ;
 UnLock : 'unlock' ;

 Number : DIGIT+ ;

 Identifier :   Letter LetterOrDigit*;

 Letter :   [a-zA-Z$_]
	|   ~[\u0000-\u00FF\uD800-\uDBFF]
		{Character.isJavaIdentifierStart(_input.LA(-1))}?
	|   [\uD800-\uDBFF] [\uDC00-\uDFFF]
		{Character.isJavaIdentifierStart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}? ;

 LetterOrDigit: [a-zA-Z0-9$_]
	|   ~[\u0000-\u00FF\uD800-\uDBFF]
		{Character.isJavaIdentifierPart(_input.LA(-1))}?
	|    [\uD800-\uDBFF] [\uDC00-\uDFFF]
		{Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?;

 fragment DIGIT: ('0'..'9');

 AT : '@';
 ELLIPSIS : '...';
 WS  :  [ \t\r\n\u000C]+ -> skip;
 Comment :   '/*' .*? '*/' -> skip;
 Line_Comment :   '//' ~[\r\n]* -> skip;

 fragment ESCQUOTE : '\\"';
 StrLiteral :   '"' ( ESCQUOTE | ~('\n'|'\r') )*? '"';
