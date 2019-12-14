import os
import zipfile
import patoolib
import shutil


PATH = './קבוצה1'  # PATH -> the folder you want to make the convert

def zipdir(directory, ziph):
    # ziph is zipfile handle
    rootdir = os.path.basename(directory)
    for dirpath, dirnames, filenames in os.walk(directory):
        for filename in filenames:

            filepath   = os.path.join(dirpath, filename)
            parentpath = os.path.relpath(filepath, directory)
            arcname    = os.path.join(rootdir, parentpath)
            ziph.write(filepath,arcname)

def folderContent():

    files = os.listdir(PATH)
    for name in files:
        
        if name.find('.RAR') != -1:
            print(name)
            justName = name[:-4]
            os.mkdir('./tempfolder')
            patoolib.extract_archive(PATH+ '/'+name, outdir="./tempfolder")   # extract rar folder
            zipf = zipfile.ZipFile(PATH+'/'+justName+'.ZIP', 'w', zipfile.ZIP_DEFLATED)
            zipdir('./tempfolder/', zipf)
            zipf.close() 
            shutil.rmtree('./tempfolder/')  ## delete temp folder
            os.remove(PATH+'/'+name)  # remove the rar file

if __name__ == '__main__':
    folderContent()
