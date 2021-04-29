(* ::Package:: *)

f[x_]:=Exp[-I x];


trainingset={1->"A",2->"A",3.5->"B",4->"B",10->"C",9->"C"};


c=Classify[trainingset]
