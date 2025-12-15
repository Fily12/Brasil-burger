# Brasil Burger — Exécution locale

Petit projet Java (CLI) pour gérer un catalogue de burgers, menus, compléments et commandes.

## Prérequis
- Java 17+ installé (vérifier avec `java -version`).
- Maven installé pour les commandes ci‑dessous (vérifier avec `mvn -v`).

## Compiler et exécuter (avec Maven)
Depuis la racine du projet (`demo/`) :

```bash
# Compiler et emballer (sans tests)
mvn -DskipTests package

# Exécuter (via le répertoire classes produit)
java -cp target/classes com.brasilburger.Main
```

Remarque : vous pouvez aussi lancer via le plugin exec :

```bash
mvn -DskipTests exec:java
```

## Alternative sans Maven (compilation manuelle)
Si vous n'avez pas Maven, compilez et exécutez avec `javac` / `java` :

```powershell
# Exemple PowerShell (Windows)
javac -d out (Get-ChildItem -Recurse -Filter *.java -Path src\main\java | Select-Object -ExpandProperty FullName)
java -cp out com.brasilburger.Main
```

## Notes
- Le projet utilise des implémentations en mémoire pour les services (pas de base de données).
- Si `mvn` n'est pas disponible, installez Maven depuis https://maven.apache.org/install.html

Si vous voulez, je peux :
- ajouter un `pom.xml` avec un plugin `exec` configuré (actuellement le projet est minimal),
- ajouter des tests unitaires simples,
- créer une branche Git et commit les changements.
