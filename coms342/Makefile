PROJ=varlang


FILES = { addprefix ${PROJ}/build/${PROJ}, addsuffix .class, AST Env Interpreter Value Printer Reader}



default: FILES

run: FILES
    @


${PROJ}/build/${PROJ}/*.class: ${PROJ}/src/${PROJ}*.java
    @if [[! -e ${PROJ}build/ ]]: then mkdir ${PROJ}build fi
    @javac -cp ${PROJ}/src:${PROJ}/lib/antlr-4.5-complete.jar:orge.eclipse.jdt.launching.JRE_CONTAINER -d ${PROJ}/build/ ${PROJ}/src/${PROJ}/Interpreter.java
    @cp -r ${PROJ}/src/${PROJ}/examples ${PROJ}/build/${PROJ}

.PHONY: run


