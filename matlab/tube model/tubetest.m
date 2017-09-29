

%define a tube

l=0.17;

c=340;

td=l/c;

A1=1;
A2=2;

r=(A1-A2)/(A1+A2);
r=1;

% u0=chirp(0,100,T1,F1);
% 
t = 0:0.0005:20;            % 2 secs @ 2kHz sample rate
y = chirp(t,0,20,10000);     % Start @ DC, 
%                           %   cross 150Hz at t=1 sec
% spectrogram(y,256,250,256,2E3,'yaxis') 


uf=zeros(1,40000);
ub=zeros(1,40000);
ub=zeros(1,40000);
uf(1)=1;
ub(1)=0;
u(1)=0;

for k=2:40000
    
%    uf(k)=y(k)-ub(k-1);
%    uf(k)=sin(2*3.14*100/2000*k)+.1*(rand-0.5)-ub(k-1);
    uf(k)=2*(rand-0.5)-ub(k-1);
    ub(k)=-r*uf(k-1);
    u(k)=uf(k)+ub(k);
    
end

