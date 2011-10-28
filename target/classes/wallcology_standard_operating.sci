clear
//disp("Running population model for WallCology...");



//////////////////////
// Define the model //
//////////////////////
function xdot=simpleModel(t,x,r1,r2,k1,k2,al11,al12,al21,al22,a11,a12,a21,a22,d1,d2,c1,c2,a11,a21,a12,a22)
    xdot=[
    x(1)*(r1*(1-al11*x(1)/k1-al12*x(2)/k1)-a11*x(3)-a12*x(4));
    x(2)*(r2*(1-al21*x(1)/k2-al22*x(2)/k2)-a21*x(3)-a22*x(4));
    x(3)*(c1*a11*x(1)+c1*a21*x(2)-d1);
    x(4)*(c2*a12*x(1)+c2*a22*x(2)-d2)
    ]
endfunction



/////////////////////////////////////////////////
// Initial populations and evaluation interval // 
/////////////////////////////////////////////////
x0=[30;30;5;5]
t0=0;
tf=500;
t = t0:0.05:tf;



///////////////////////////
// Parameters assignment //
///////////////////////////
r1=.1;
r2=.1;

k1=95;
k2=105;

al11=1;
al12=.3;
al21=.4;
al22=1;

a11=.01;
a12=.004;
a21=.005;
a22=.012;

d1=.4;
d2=.4;

c1=1;
c2=1;



//////////////////////
// Solve the system //
//////////////////////
lst=list(simpleModel,r1,r2,k1,k2,al11,al12,al21,al22,a11,a12,a21,a22,d1,d2,c1,c2,a11,a21,a12,a22) 
sol=ode(x0,t0,t,lst);
//Corrections
corr1=1;
corr2=1;
corr3=1;
corr4=1;

sol(1,:)=corr1*sol(1,:);
sol(2,:)=corr2*sol(2,:);
sol(3,:)=corr3*sol(3,:);
sol(4,:)=corr4*sol(4,:);



////////////////////////////////
// Plot the population curves //
////////////////////////////////
clf();
plot2d(t, sol', [2,5,2,5]);
// Change thickness
a=gca();
line= a.children(1).children(1);
line.thickness = 3; 
line= a.children(1).children(2);
line.thickness = 3; 
// Add legend
hl=legend(['Scum';'Fuzz';'Scum eaters'; 'Fuzz eaters']);


// -----------------------------------------------------------


// Stability... 
A=[ -r1*al11/k1, -r1*al12/k1, -a11, -a12;
    -r2*al21/k2, -r2*al22/k2, -a21, -a22;
    c1*a11,      c1*a12,      0,    0;
    c2*a21,      c2*a22,      0,    0]
    
r = [r1; r2; -d1; -d2]
    
y = -A\r

eig = spec(A)

disp(y)
disp(eig)