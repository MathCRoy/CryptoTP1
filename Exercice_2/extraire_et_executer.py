import os
import sys
import subprocess
from stegano import lsb

dossier_depart = "fakeOrdi"
nom_script_reconstruit = "script_secret_reconstruit.py"

# Recherche
liste_images_png = []
for dossier_courant, sous_dossiers, fichiers in os.walk(dossier_depart):
    for nom_fichier in fichiers:
        if nom_fichier.endswith(".png"):
            chemin_complet = os.path.join(dossier_courant, nom_fichier)
            liste_images_png.append(chemin_complet)

print("images .png trouvées :", len(liste_images_png))

# Extraction
morceaux_trouves = []
for chemin_image in liste_images_png:
    try:
        texte_cache = lsb.reveal(chemin_image)
    except Exception:
        texte_cache = None

    # le numéro de partie est le premier chiffre du nom de fichier
    nom_fichier = os.path.basename(chemin_image)
    premier_caractere = nom_fichier[0]
    if not premier_caractere.isdigit():
        continue

    numero_partie = int(premier_caractere)
    morceaux_trouves.append([numero_partie, texte_cache])
    print("morceau", numero_partie, "extrait de", chemin_image)

# Reconstruction
morceaux_trouves.sort()

script_complet = ""
for numero_partie, texte_cache in morceaux_trouves:
    script_complet = script_complet + texte_cache

fichier_sortie = open(nom_script_reconstruit, "w")
fichier_sortie.write(script_complet)
fichier_sortie.close()

print("script reconstruit dans", nom_script_reconstruit)

# Exécution
subprocess.run(["python3", nom_script_reconstruit, dossier_depart])
