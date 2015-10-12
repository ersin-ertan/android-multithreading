
package com.nullcognition.lightweight_stream_api;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.annimon.stream.Stream;

import java.util.Random;

public class MainActivity extends AppCompatActivity{

	private Spinner actionSpinner;
	private SeekBar seekBar;

	private ListView    listView;
	private WordAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		seekBar = (SeekBar) findViewById(R.id.filterSeekBar);

		final String[] actions = getResources().getStringArray(R.array.actionValues);
		actionSpinner = (Spinner) findViewById(R.id.actionSpinner);
		actionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
			@Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
				if(actions[i].contains("%N")){ seekBar.setVisibility(View.VISIBLE);}
				else{ seekBar.setVisibility(View.GONE);}
			}

			@Override public void onNothingSelected(AdapterView<?> adapterView){

			}

		});
		findViewById(R.id.go).setOnClickListener(view -> {
			final int index = actionSpinner.getSelectedItemPosition();
			if(index != Spinner.INVALID_POSITION){
				action(actions[index]);
			}
		});

		adapter = new WordAdapter(this, Utils.readWords(this));
		listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(adapter);

		findViewById(R.id.distinct).setOnClickListener(view -> {
			Stream.of(adapter.getCurrentList())
			      .distinct()
			      .collect(Utils.collectAdapter(adapter));
		});
		findViewById(R.id.sort).setOnClickListener(v -> {
			Stream.of(adapter.getCurrentList())
			      .sorted()
			      .collect(Utils.collectAdapter(adapter));
		});
		findViewById(R.id.info).setOnClickListener(v -> {
			long all = adapter.getWords().size();
			long list = listView.getCount();
			String text = String.format("%d items all\n%d items in list", all, list);
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
		});
	}

	private void action(String action){
		final int filterValue = seekBar.getProgress();

		final long   time   = System.currentTimeMillis();
		Stream<Word> stream = Stream.of(adapter.getWords());
		switch(action){
			case "filter 1":
				// Filter one word
				stream = stream.filter(p -> p.getWord().split(" ").length == 1);
				break;
			case "filter 2+":
				// Filter two and more words
				stream = stream.filter(p -> p.getWord().split(" ").length >= 2);
				break;
			case "filter %N words":
				// Filter 1 .. 10 words
				final int words = (filterValue / 10 + 1);
				stream = stream.filter(p -> p.getWord().split(" ").length == words);
				break;
			case "translate length":
				// Replace word by translate length
				stream = stream.map(p ->
						p.setWord(String.valueOf(p.getTranslate().length())));
				break;
			case "filter %N length":
				// Filter by word length
				stream = stream.filter(p -> p.getWord().length() == filterValue);
				break;
			case "contains ok":
				// Set answer to translate row
				stream = stream.map(p -> p.setTranslate(p.getWord().contains("ok") ? "yes" : "no"))
				               .sorted((w1, w2) -> {
					               boolean b1 = w1.getTranslate().equals("yes");
					               boolean b2 = w2.getTranslate().equals("yes");
					               if(b1 == b2){ return 0; }
					               return (b2 ? 1 : -1);
				               });
				break;
			case "add index":
				stream = Stream.ofRange(0, adapter.getCount())
				               .map(i -> String.format("%d. %s", i + 1, adapter.getItem(i).getWord()))
				               .map(str -> new Word(str, ""));
				break;
			case "skip %N":
				stream = stream.skip(filterValue);
				break;
			case "limit %N":
				stream = stream.limit(filterValue);
				break;
			case "group":
				// Show 5 words by each group
				stream = Stream.ofRange('a', 'z' + 1)
				               .map(i -> String.valueOf((char) i.shortValue()))
				               .flatMap(s -> Stream.of(adapter.getWords())
				                                   .filter(w -> w.getWord().startsWith(s))
				                                   .limit(5))
				               .map(w -> new Word(String.valueOf(w.getWord().charAt(0)), w.getWord()));
				break;
			case "group by":
				stream = stream.groupBy(w -> w.getWord().charAt(0))
				               .flatMap(entry -> Stream.of(entry.getValue())
				                                       .map(w -> new Word(String.valueOf(entry.getKey()), w.getWord())))
				               .sortBy(Word::getWord);
				break;
			case "sort by":
				stream = stream.sortBy(Word::getTranslate);
				break;
			case "random":
				final Random rnd = new Random();
				stream = Stream.generate(() -> rnd.nextInt(100))
				               .limit(10000)
				               .map(String::valueOf)
				               .map(s -> new Word(s, ""));
				break;
		}
		stream.collect(Utils.collectAdapter(adapter));
		listView.invalidate();
		String msg = getString(R.string.done_format, (System.currentTimeMillis() - time) / 1000f);
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
}
