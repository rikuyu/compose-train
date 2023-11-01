TARGET=$(find . -type f | grep -v 'build/')

for FILE in $TARGET; do
  perl -i -pe 's/ object (.+)/data object $1/  unless /^.+(expect|companion|data)/' "$FILE"
done

echo "********* Finished *********"
