library(ggplot2)

d <- read.table("HW2Q2.dat",header=TRUE,sep=" ")
ggplot(d, aes(x=x, y=y)) + geom_point(cex=3)
T <- lm(y~poly(x,2),data=d)
yhat <- predict(T,d)
plot(d, pch = 16, col = "blue") #Plot the results

lines(x = d$x,y= yhat,col = "darkgreen", lwd = 3)
summary(T)


#Runs Test
layout(matrix(c(1),1,1))
acf(resid(T),type=c("correlation"))
library(lawstat)
runs.test(resid(T))
