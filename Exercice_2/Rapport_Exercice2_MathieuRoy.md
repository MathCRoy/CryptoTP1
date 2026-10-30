# méthodologie

## Phase 1: cacher_morceaux_dans_images.py
J'ai suivi la stratégie dans la section 2 de l'énoncé, pour chaque image en utilisant des images d'albums que j'avais téléchargées dernièrement en utilisant la librairie Stegano.
Simplement de pointer vers les bonnes images vers les fonctions de la librairie a fait l'affaire. Une fois la bonne méthode trouvée, la génération de fichier a été faite.

## Phase 2: extraire_et_executer.py
J'ai suivi la stratégie dans la section 4 de l'énoncé et ce, dans le dossier EX2/fakeOrdi 
4. Reconstruction et exécution du script
    — Recherche
        Je cherche les dossiers récursivement pour trouver les .png infectés que j'ai créés à la phase 2
        Ce bout de recherche est simplifié, je devrais probablement injecter une sorte de marque dans l'entête de mes fichiers infectés au lieu de me fier qu'il y ait seulement mes .png
    — Extraction
        Avec les fichiers retrouvés, je peux extraire les parties de code malicieux en me basant sur le nom de fichier. Encore une fois, si cette association (fichier retrouvé -> position logique du code malicieux injecté dans l'image) avait été faite en utilisant une marque dans l'entête du fichier, ça serait bien meilleur.
    — Reconstruction
        Avec toutes les parties de code malicieux, je peux concaténer le code malicieux original et le sauvegarder dans un nouveau fichier.
    — Exécution
        Exécuter le fichier nouvellement créé.

# défis rencontrés
Premièrement, je n'utilise presque jamais Python. La syntaxe et son utilisation a pris du temps à se faire réapprendre.
Deuxièmement, de trouver comment utiliser la librairie de stegano m'a pris un bon moment.
Finalement, penser et découvrir comment "garder en tête" où se trouve le code malicieux injecté a été un bon casse-tête.

# les implications de la stéganographie en matière de sécurité
Je vois comment on peut utiliser la stéganographie pour cacher du code malicieux dans des fichiers qui, apparemment, sont corrects.
Je ne vois pas comment un anti-virus ou autres outils de cybersécurité pourrait détecter ces changements sur des images ou un vidéo.
Par contre, j'ai de la difficulté à voir comment un attaquant pourrait déclencher la phase 2 du plan sans avoir un certain accès à la machine attaquée.
