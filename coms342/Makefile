PROJ=typelang
FOLDER=hw10/

FILES =  $(addprefix ${FOLDER}${PROJ}/build/${PROJ}/, $(addsuffix .class, AST Env Interpreter Value Printer Reader Evaluator Heap Checker))



default: makebin ${FILES} copyScripts
	@echo done

build:
	@cd ${FOLDER}${PROJ}; ant

run:
	@cd ${FOLDER}${PROJ}; java -cp lib/antlr-4.5-complete.jar:org.eclipse.jdt.launching.JRE_CONTAINER:build ${PROJ}/Checker || true

clean:
	@rm -r ${FOLDER}${PROJ}/build

${FOLDER}${PROJ}/build/${PROJ}/%.class: ${FOLDER}${PROJ}/src/${PROJ}/%.java
	@echo "compiling $<"
	@javac -Xlint:unchecked -cp ${FOLDER}${PROJ}/src:${FOLDER}${PROJ}/lib/antlr-4.5-complete.jar:org.eclipse.jdt.launching.JRE_CONTAINER -d ${FOLDER}${PROJ}/build/ ${FOLDER}${PROJ}/src/${PROJ}/Interpreter.java
	@cp -r ${FOLDER}${PROJ}/src/${PROJ}/examples ${FOLDER}${PROJ}/build/${PROJ}

makebin:
	@echo "making bin"
	@[ -d ${FOLDER}${PROJ}/build/ ] || mkdir ${FOLDER}${PROJ}/build

copyScripts:
	@echo "copying scripts"
	@cp -r ${FOLDER}${PROJ}/src/${PROJ}/examples ${FOLDER}${PROJ}/build/${PROJ}

.PHONY: run default clean makebin copyScripts
