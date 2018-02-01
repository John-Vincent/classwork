grammar VarLang;

 program returns [Program ast] :
		e=exp { $ast = new Program($e.ast); }
		;

 exp returns [Exp ast]:
		v=varexp { $ast = $v.ast; }
		| n=numexp { $ast = $n.ast; }
        | a=addexp { $ast = $a.ast; }
        | s=subexp { $ast = $s.ast; }
        | m=multexp { $ast = $m.ast; }
        | d=divexp { $ast = $d.ast; }
        | l=letexp { $ast = $l.ast; }
        | ls=letsexp { $ast = $ls.ast; }
        ;

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

 // New elements in the Grammar of this Programming Language
 //  - grammar rules start with lowercase

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

 letsexp  returns [LetsExp ast]
        locals [ArrayList<String> names, ArrayList<Exp> value_exps]
 		@init { $names = new ArrayList<String>(); $value_exps = new ArrayList<Exp>(); } :
 		'(' Lets
 			'(' ( '(' id=Identifier e=exp ')' { $names.add($id.text); $value_exps.add($e.ast); } )+  ')'
 			body=exp
 			')' { $ast = new LetsExp($names, $value_exps, $body.ast); }
 		;

 // Lexical Specification of this Programming Language
 //  - lexical specification rules start with uppercase

 Let : 'let' ;
 Lets : 'lets' ;
 Dot : '.' ;

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
