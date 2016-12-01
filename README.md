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

```java
int minSamplesSplit = 2;
int maxDepth = Integer.MAX_VALUE;
double minImpurityDecrease = 1e-07;
int minSampleLeaf = 1;
int maxFeatures = Integer.MAX_VALUE;
int nbTrees = 10;
Long seed = null;
```

#### ```new Parameter.Builder() : Builder```

Return a builder to setup the parameters of the random forest. The available
functions to update the default values are :

##### ```Builder.minSamplesSplit(int) : Builder``` </li>
##### ```Builder.maxDepth(int) : Builder``` </li>
##### ```Builder.minImpurityDecrease(double)``` : Builder </li>
##### ```Builder.minSampleLeaf(int) : Builder``` </li>
##### ```Builder.maxFeatures(int) : Builder``` </li>
##### ```Builder.seed(Long) : Builder``` </li>
##### ```Builder.nbTrees(int) : Builder``` </li>
##### ```Builder.build() : Parameter``` </li>

```java
// Builder example
Parameter p = new Parameter.Builder()
                     .nbTrees(200)
                     .maxFeatures(3)
                     .build();
```

### RandomForest

#### ```new RandomForest(Parameter) : RandomForest```

Constructor of the random forest.

#### ```RandomForest.fit(List<D>)```

Train the random forest using a list of tuples ```D```. The latter type extends
```IData<R>``` which enforces the presence of the function ```getResult() : R```.

#### ```RandomForest.predict(D) : R```

Predict the result ```R``` according to the data ```D```.

#### ```RandomForest.importance() : List<ImmutablePair<String, Double>>```

Get the list of features sorted by decreasing importance.

## Example

A usage example about Titanic survivors is available at
[broceliande-example](https://github.com/korriganed/broceliande-example).

## References

[1] Leo Breiman. Random Forests. <i>Machine Learning.</i> vol. 45,
p. 5-32. 2001.

[2] Gilles Louppe. Understanding random forests: From theory to
practice. <i>arXiv preprint arXiv:1407.7502</i>, 2014.