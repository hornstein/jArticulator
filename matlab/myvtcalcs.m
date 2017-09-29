
load tongue
load palate

t=tongue;
p=palate;

p(:,2)=-p(:,2)+450;
p(:,1)=-p(:,1)+420;
t(:,1)=-t(:,1)+420;
t(:,2)=-t(:,2)+450;
figure;plot3(t(:,1), t(:,2), zeros(size(t)))
hold on;plot3(p(:,1), p(:,2), zeros(size(p)))
daspect([1 1 1])


%transform tongue position

%pc1=-20;
pc1=0;

angle=30*pi/180;

for i=6:26
   
    t(i,1)=t(i,1)-pc1*cos(angle)
    t(i,2)=t(i,2)+pc1*sin(angle)
    
    
end

figure;plot3(t(:,1), t(:,2), zeros(size(t)))
hold on;plot3(p(:,1), p(:,2), zeros(size(p)))
daspect([1 1 1])


%set point for apex
apex_x=125
apex_y=125

%pc2=-10*pi/180;
pc2=0;

for i=6:26
    tempx=t(i,1)-apex_x;
    tempy=t(i,2)-apex_y;
    
    if(t(i,1)>t(i,2))
        tempx=tempx*cos(pc2)-tempy*sin(pc2);
        tempy=tempy*cos(pc2)+tempx*sin(pc2);
    else
        tempx=tempx*cos(-pc2)-tempy*sin(-pc2);
        tempy=tempy*cos(-pc2)+tempx*sin(-pc2);
    end
    
    t(i,1)=tempx+apex_x;
    t(i,2)=tempy+apex_y;
    
end

figure;plot3(t(:,1), t(:,2), zeros(size(t)))
hold on;plot3(p(:,1), p(:,2), zeros(size(p)))
daspect([1 1 1])




%transform vocal tract to polar coordinates

pv=abs(atan(p(:,2)./p(:,1)));
pd=sqrt(p(:,2).^2+p(:,1).^2);

tv=abs(atan(t(:,2)./t(:,1)));
td=sqrt(t(:,2).^2+t(:,1).^2);

figure
pv=abs(pv)
plot(pv,pd)

hold on
plot(tv,td)

%calculate area function

d=16;

A=ones(d,1);

step=1.5/d;

for i=1:d
    
   x=i*step;
   
   %find palate pos
   for j=1:length(pv)-1
       if( pv(j)<x && pv(j+1)>=x)
           %should be interpolated
           py=pd(j+1);
       end     
   end
       
   %find tongue pos
   for j=1:length(tv)-1
       if( tv(j)<x && tv(j+1)>=x)
           %should be interpolated
           ty=td(j+1);
       end     
   end
   
   %check collision
   if(ty>py)
       ty=py;
   end
       
   A(i)=(py-ty)^2;
    
end

figure
bar(A)

