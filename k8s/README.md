# K8s

We provide you with a helm chart that will let you run the compiler in a distributed way on K8s.

```shell
helm install compiler ./helm_charts/compiler_chart
```

Note if you are running k8s using Minikube :
* you can reuse the Docker daemon from Minikube by running this command : **eval $(minikube docker-env)**.
* set image pull policy to **Never** in the values.yaml file.