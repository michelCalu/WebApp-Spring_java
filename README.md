# Hermes (**ajouter phrase d'accroche**)

## Guide d'installation

Afin de pouvoir contribuer au projet Hermes, il faut passer par plusieurs étapes.

### I. Git

Pour plus de faciliter il est recommander de créer une clé ssh.
Pour ce faire, il suffit de suivre les instruction fournies par Git: 
  https://help.github.com/articles/connecting-to-github-with-ssh/

Il vous suffit ensuite de cloner le projet :
  `git clone git@github.com:fgermeau/hermes.git`

### II. Intellij

Le projet Hermes est principalement codé en Java.
Vous pouvez utiliser l'IDE avec lequel vous avez le plus d'affinité mais dans la suite de ce guide d'installation, j'expliquerai l'installation pour Intellij.

Vous devez donc télécharger Intellij.
Mais, afin de pouvoir déployer facilement l'application sur tomcat (serveur utilisé pour le projet), vous devez télécharger la version ultimate d'Intellij qui est payante.
Fort heureusement, si vous êtes étudiant ou enseignant, vous pouvez bénéficier d'une license gratuite vous donnant accès à tous les IDE de JetBrains.

Commencez par vous créer un compte sur le site d'Intellij en suivant ce lien. **Insérer lien + expliquer la procédure de téléchargement**

Une fois Intellij installé, il reste plus qu'à ouvrir le projet (**pas** importer) Hermes cloné à la section précédente.

### III. Maven

Le gestionnaire de projet utilisé pour le projet Hermes est Maven. Il faut donc utiliser des commandes Maven pour compiler, tester, déployer... le projet.
Fort heureusement, il existe dans Intellij une vue qui reprend toutes ces commandes vous permettant d'effecuter ces opérations en un simple clic.
Pour accéder à ce panel, il faut aller dans `view -> Tool windows -> Maven projects`. Normalement vous devriez voir une arborescence avec le module master et ses sous-modules.

Pour compiler le projet cliquez sur `compile`.

### IV. Tomcat

Comme indiqué plus haut, on utilise Tomcat pour déployer notre application web.
Vous devrez dans un premier temps télécharger Tomcat. **insérer lien**
Vous pouvez prendre la dernière version.

Ceci fait, il faut ajouter le serveur Tomcat dans Intellij :
  Allez dans `Files -> Settings -> Build, Execution, Deployement -> Application server`
  Ensuite créer une nouvelle configuration de serveur Tomcat en cliquant sur le petit '+' (référencez le dossier d'installation de Tomcat).

Puis définir la manière dont vous voulez lancer l'application :
  Ici on veut déployer l'application sur un serveur Tomcat : `run -> Edit Configurations...`
  Ensuite créez votre serveur Tomcat en cliquant sur le petit '+' -> Tomcat server
  Dans l'écran de création du serveur, allez dans `Deployement` et ajouter un `Artefact -> war ou war exploded` (les deux sont bons)

Il ne vous reste plus qu'à appuyer sur 'run' ! :)
