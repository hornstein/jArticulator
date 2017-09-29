t1=tubemodel.Tube;
t2=tubemodel.Tube;
j12=tubemodel.Junction(t1, t2);

if (exist('t1')<1)
    t1=tubemodel.Tube;
end
if (exist('t2')<1)
    t2=tubemodel.Tube;
end
if (exist('j12')<1)
    j12=tubemodel.Junction(t1, t2);
end

load u1;

%initialize vocal tract model

Fs=10000;

t1.area=1;
t1.length=0.085;
t1.fs=Fs;
t2.area=4;
t2.length=0.085;
t2.fs=Fs;


%run a simulation

y=zeros(Fs+1,1);
y1=zeros(Fs+1,1);
y2=zeros(Fs+1,1);
y3=zeros(Fs+1,1);

for i=1:length(y-1)
   
    %update source
    t1.ufin=u1(i)-t1.ubout;
    %update mouth
    if(i>1)
        t2.ubin=0.5*y(i-1);
    end
    %update junction
    j12.calculate();
    
    %simulate one step
    t1.step();
    t2.step();
    
    j12.calculate();    
    y(i)=t2.ufout;
    
    y2(i)=t1.ufout;
    y3(i)=t2.ubin;
    
end


