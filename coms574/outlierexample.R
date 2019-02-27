x <- seq(-3,3,length.out=200)
y <- 11+5*x+rnorm(200,0,1)
# Put 3 outliers in the data set
y[195] <- 50
y[198] <- 40
y[200] <- 60

y[3] <- -17
y[5] <- -57

# put 2 leverage points
x[50] <- 50
x[100] <- 60
x[10] <- -30
x[1] <- -90

T1 <- lm(y~x)

layout(matrix(c(1),1,1))
plot(x,y)
lines(x,T1$coefficients[[1]]+T1$coefficients[[2]]*x, type="l", col = "red")

layout(matrix(c(1,2,3,4),2,2))
plot(T1)

install.packages("quantreg")
library(quantreg)
r1 <-rq(y ~ x, tau=0.5)
lines(x,T1$coefficients[[1]]+T1$coefficients[[2]]*x, type="l", col = "red")
lines(x,r1$coefficients[[1]]+r1$coefficients[[2]]*x, type="l", col = "blue")
