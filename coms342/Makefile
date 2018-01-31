PROJ=varlang


FILES =  $(addprefix ${PROJ}/build/${PROJ}/, $(addsuffix .class, AST Env Interpreter Value Printer Reader))



default: ${FILES}
	@echo done

run: 
	@java -cp ${PROJ}/lib/antlr-4.5-complete.jar:org.eclipse.jdt.launching.JRE_CONTAINER ${PROJ}/build/Interpreter

${PROJ}/build/${PROJ}/%.class: ${PROJ}/src/${PROJ}/%.java
	@ [ -d ${PROJ}/build/ ] || mkdir ${PROJ}/build
	@javac -cp ${PROJ}/src:${PROJ}/lib/antlr-4.5-complete.jar:org.eclipse.jdt.launching.JRE_CONTAINER -d ${PROJ}/build/ ${PROJ}/src/${PROJ}/Interpreter.java
	@cp -r ${PROJ}/src/${PROJ}/examples ${PROJ}/build/${PROJ}

.PHONY: run


