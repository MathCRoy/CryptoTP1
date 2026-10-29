from stegano import lsb

# Images sources copiées d'un autre projet de session en cours

# Partie 1
fichier1 = open("partie_1.txt", "r")
texte1 = fichier1.read()
fichier1.close()
image1 = lsb.hide("1.jpg", texte1)
image1.save("1changed.png")
print("partie 1 cachée dans 1changed.png")

# Partie 2
fichier2 = open("partie_2.txt", "r")
texte2 = fichier2.read()
fichier2.close()
image2 = lsb.hide("2.jpg", texte2)
image2.save("2changed.png")
print("partie 2 cachée dans 2changed.png")

# Partie 3
fichier3 = open("partie_3.txt", "r")
texte3 = fichier3.read()
fichier3.close()
image3 = lsb.hide("3.jpg", texte3)
image3.save("3changed.png")
print("partie 3 cachée dans 3changed.png")
