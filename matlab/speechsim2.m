
%create new tube
if (exist('ts')<1)
    ts={}
    junc={}
    nrs=16;
    for i=1:nrs
        ts{i}=tubemodel.Tube;
    end
    
    for i=1:nrs-1
        junc{i}=tubemodel.Junction(ts{i}, ts{i+1});
    end
end

%load source
%load u1;

%load area function
load AF;

%initialize vocal tract model

Fs=34000;

for i=1:nrs
    if(i<nrs/2)
        ts{i}.area=1;
    else
        ts{i}.area=4;
    end
%    ts{i}.area=AF(i);
    ts{i}.length=0.01;
    ts{i}.fs=Fs;
end
 

%run a simulation

y=zeros(10000+1,1);

for i=1:length(y-1)
   
    %update source
    myi=floor(i/3.4)+1;
    ts{1}.ufin=u1(myi)-ts{1}.ubout;
    %ts{1}.ufin=u1(i)-ts{1}.ubout;
    %update mouth
    if(i>1)
        ts{nrs}.ubin=0.3*y(i-1);
    end
    
    for j=1:nrs-1
        %update junction
        junc{j}.calculate();
    end
    
    for j=1:nrs
        %simulate one step
        ts{j}.step();
    end
    
    y(i)=ts{nrs}.ufout;
    
    
end