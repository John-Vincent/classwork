

%token IDENT

%%

func_call: IDENT '(' args ')';

args: %empty
    | arg_list;

arg_list: func_call
    | arg_list ',' func_call;
