.PHONY: test

test:
	clj -A\:test

repl:
	clj -A\:repl

release: test
	mvn deploy
