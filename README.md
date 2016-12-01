# broceliande

<i>Keywords: random forest, decision tree, prediction</i>

This project provides a Java implementation of random forests [1, 2]. Random
forests use training sets to build decision trees. Given an input (e.g. a person
with age, gender, medical background, symptoms) the result (e.g. a disease) of
which is unknown, random forests are able to predict the corresponding result.

## API

### Parameter

The parameters that will be used to build random forests. The default values are
:

```
		minSamplesSplit = 2;
		maxDepth = Integer.MAX_VALUE;
		minImpurityDecrease = 1e-07;
		minSampleLeaf = 1;
		maxFeatures = Integer.MAX_VALUE;
		nbTrees = 10;
		seed = null;
```

#### ```new Paramater.Builder() : Builder```

Return a builder to setup the parameters of the random forest.

##### ```Builder.minSamplesSplit(int) : Builder``` </li>
##### ```Builder.maxDepth(int) : Builder``` </li>
##### ```Builder.minImpurityDecrease(double)``` : Builder </li>
##### ```Builder.minSampleLeaf(int) : Builder``` </li>
##### ```Builder.maxFeatures(int) : Builder``` </li>
##### ```Builder.seed(Long) : Builder``` </li>
##### ```Builder.nbTrees(int) : Builder``` </li>
##### ```Builder.build() : Parameter``` </li>

```java
// Example
   Parameter p = new Parameter.Builder()
                       .nbTrees(200)
                       .maxFeatures(3)
                       .build();
```

### RandomForest

## Example

A usage example about Titanic survivors is available at
[broceliande-example](https://github.com/korriganed/broceliande-example).

## References

[1] Leo Breiman. Random Forests. <i>Machine Learning.</i> vol. 45,
p. 5-32. 2001.

[2] Gilles Louppe. Understanding random forests: From theory to
practice. <i>arXiv preprint arXiv:1407.7502</i>, 2014.