.PHONY: test

test:
	clj -A\:test

repl:
	clj -A\:repl

deploy: test
	mvn deploy
