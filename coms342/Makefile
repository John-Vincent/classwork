PROJ=funclang


FILES =  $(addprefix ${PROJ}/build/${PROJ}/, $(addsuffix .class, AST Env Interpreter Value Printer Reader))



default: makebin copyScripts ${FILES}
	@echo done

run:
	@cd ${PROJ}; java -cp lib/antlr-4.5-complete.jar:org.eclipse.jdt.launching.JRE_CONTAINER:build ${PROJ}/Interpreter || true

clean:
	@rm -r ${PROJ}/build

${PROJ}/build/${PROJ}/%.class: ${PROJ}/src/${PROJ}/%.java
	@echo "compiling $<"
	@javac -cp ${PROJ}/src:${PROJ}/lib/antlr-4.5-complete.jar:org.eclipse.jdt.launching.JRE_CONTAINER -d ${PROJ}/build/ ${PROJ}/src/${PROJ}/Interpreter.java
	@cp -r ${PROJ}/src/${PROJ}/examples ${PROJ}/build/${PROJ}

makebin:
	@echo "making bin"
	@[ -d ${PROJ}/build/ ] || mkdir ${PROJ}/build

copyScripts:
	@echo "copying scripts"
	@cp -r ${PROJ}/src/${PROJ}/examples ${PROJ}/build/${PROJ}

.PHONY: run default clean makebin copyScripts
