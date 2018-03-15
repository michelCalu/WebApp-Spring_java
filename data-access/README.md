# Module Data-Access

## main/java/.../dataacces

### Config
Le package `config`, contient le fichier de configuration de la couche data-access.

Outre l'annotation `Configuration` qui spécifie uniquement à Spring que cette classe est une classe de configuration, 
on retrouve l'annotation `@ComponentScan(...)` qui spécifie où Spring doit chercher certaines classes.

Dans ce cas-ci l'annotation complète est `@ComponentScan(basePackages = {"be.unamur.hermes.dataaccess.repository"})
`. Les classes à considérer sont donc des classes contenues dans le packages repository.

### Entity
Le package `entity`, comme son nom l'indique contient les entités de l'application, c'est-à-dire les objets que l'on va
manipuler dans le business.

Chaque entitée contiendra évidemment ses propres attributs, getters, setters ainsi que toutes méthodes modifiant
directement l'objet. Attention pas de logique métier !

ex : 
- public void activate() : Pour valider l'inscription d'un citoyen peut se retrouver dans la classe de l'entité citoyen.
Elle changera simplement l'attribut `activated` de false à true.
- public boolean checkInhabitantInformation() : Qui vérifie que les informations entrées par le citoyen sont correctes
avant de activer le profil du citoyen devra se trouver dans le module business et pas dans l'entité inhabitant.

### Repository
Le package `repository` va contenir les classes qui vont intéragir directement avec la base de données SQL.
On y retrouvera donc des méthodes "finder" du style `findUserByName()`, des méthodes d'update, de création, ... 
Bref tout ce qui concerne une intéraction avec la base de données.

Chaque classe (pas interface) doit être annotée de l'annotation `@Repository` pour être correctement interprétée
par Spring. De plus chaque classe aura besoin d'un objet `JdbcTemplate` qui facilite l'interraction avec la
base de données. La dépendance de cet objet est injecté via l'annotation `Autowired` au niveau du constructeur
de la classe.

## main/resources

### schema.sql
Le fichier schema.sql contient le script SQL de création de la base de données.
Il s'agit de simples commande MySQL qui vont générer la base de données.

On peut avoir plusieurs fichiers de ce type, chaque fichier pourrait correspondre à un environnement particulier :
- Test
- Dev
- Prod
- ...

Afin de pouvoir déployer/redéployer l'application sans soucis de duplication de table (MySQL ne l'autorisera sans doute
pas et renverra une erreur au déploiement), il faut dropper la chaque table avant de la recréer. Il suffit de rajouter
la ligne `DROP TABLE IF EXISTS t_xxx;`.

### data.sql
Similairement à schema.sql, data.sql va se charger de remplir la base de donnée avec des données.

On peut également créer différents fichiers pour différents environnements.

## Test (TODO quand il y aura de la matière)
