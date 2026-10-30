# Description du script :
#
# Fonction find_files(start_path, extension):
#   Parcourt récursivement le système de fichiers à partir du chemin spécifié
#   pour trouver tous les fichiers avec l'extension donnée.
#
# Fonction calculate_file_hash(filepath):
#   Calcule le hash SHA-256 du fichier spécifié, ce qui peut être utilisé
#   pour vérifier l'intégrité des fichiers.
#
# Fonction main():
#   Gère les arguments de ligne de commande, recherche les fichiers .pdf
#   et affiche leur chemin et leur hash.


import os
import sys
import hashlib

def find_files(start_path, extension):
    """Parcourt le système de fichiers pour trouver les fichiers avec l'extension donnée."""
    matches = []
    for root, dirs, files in os.walk(start_path):
        for filename in files:
            if filename.endswith(extension):
                matches.append(os.path.join(root, filename))
    return matches

def calculate_file_hash(filepath):
    """Calcule le hash SHA-256 du fichier spécifié."""
    sha256_hash = hashlib.sha256()
    with open(filepath, "rb") as f:
        for byte_block in iter(lambda: f.read(4096), b""):
            sha256_hash.update(byte_block)
    return sha256_hash.hexdigest()

def main():
    if len(sys.argv) != 2:
        print("Usage: python script_secret.py [chemin_démarrage]")
        sys.exit(1)

    start_path = sys.argv[1]
    files = find_files(start_path, ".pdf")
    print(f"Fichiers trouvés avec l'extension .pdf dans {start_path}:")

    for file in files:
        file_hash = calculate_file_hash(file)
        print(f"{file}: {file_hash}")

if __name__ == "__main__":
    main()
