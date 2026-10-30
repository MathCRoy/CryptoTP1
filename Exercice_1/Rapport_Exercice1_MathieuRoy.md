# Manière d'arriver au code de désactivation
Le premier indice était par décalage. J'ai fait un algo qui écrit toutes les possibilitées et j'ai remarqué qu'avec i = 5, le message contient "3 secondes".
Indice 1 = 3

Le deuxière indice était par Affine. Même idée que le premier, j'ai un algo qui a écrit toutes les possibilités et j'ai remarqué qu'avec (11,3), le texte mentionne "nombre d'étape dans ce premier exercice" -> 5

Troisième idice avec Hill. J'ai appliqué le chiffrement, j'ai même les bons premiers caractères mais peu importe comment je repositionne les lettres, j'ai: 

B K
K W
C I
D X
M C
G W
A N
U Q
I C
D X
C L
Q V
I R

J'en conclus donc que je prendrais un chiffre au hazard de 4 lettres, soit zéro, deux, cinq, sept, huit ou neuf

Quatrième indice: J'ai créer le tableau Polybe puis pour chaque lettre de son message, j'ai compté la position x et y (indice 1). Le nombre le plus comun des x et y trouvé était 6

Cinquième indice, j'ai utilisé Kasiki et IC pour trouver une longeur de clé de Vigenère de 6. Ensuite, j'ai grouppé tous les caractères à la même position des groupes de 6 du messages et j'y ai trouvé la lettre la plus commune. J'ai ensuite estimé que cette lettre était un décalage de la lettre e originale et j'en ai calculé le décalage. J'ai trouvé la clé "ORANVI", qui donnait un message incomplet, mais certains groupes de lettres ressemblaient à du français. J'ai essayé avec la clé similaire "ORANGE" et le message est apparus. Il mentionnait 7h15, j'ai donc pensé que le chiffre était 7.

Code final: 3 5 ? 6 7